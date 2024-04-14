/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.ct600.v3.calculations

import uk.gov.hmrc.ct.CATO05
import uk.gov.hmrc.ct.box.{CtInteger, CtOptionalInteger, CtTypeConverters}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.NumberRounding
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper._
import uk.gov.hmrc.ct.ct600.calculations.Ct600AnnualConstants._
import uk.gov.hmrc.ct.ct600.calculations.{CtConstants, TaxYear}
import uk.gov.hmrc.ct.ct600.v3._

import java.time.LocalDate
import java.time.temporal.ChronoUnit


trait MarginalRateReliefCalculatorV3 extends CtTypeConverters with NumberRounding {

  def computeMarginalRateReliefV3(b315: B315, b335: B335, b385: B385, b326: B326,b327:B327,b328:B328,b620:B620, accountingPeriod: HmrcAccountingPeriod): CATO05 = {
    validateAccountingPeriod(accountingPeriod)

    val fy1: Int = startingFinancialYear(accountingPeriod.start)
    val fy2: Int = endingFinancialYear(accountingPeriod.end)
    var fy1Result=BigDecimal(0)
    var fy2Result=BigDecimal(0)

    if (fy2 != fy1){
      val fy1Constants = constantsForTaxYear(TaxYear(fy1))
      val fy2Constants = constantsForTaxYear(TaxYear(fy2))
      val differentUpperLimits = fy1Constants.upperRelevantAmount != fy2Constants.upperRelevantAmount

      fy1Result = calculateForFinancialYear(fy1,b335, b315, b327,b620, accountingPeriod, fy1Constants, differentUpperLimits)
      fy2Result = calculateForFinancialYear(fy2,b385, b315, b328,b620, accountingPeriod, fy2Constants, differentUpperLimits)
    }
    else{
      fy1Result = calculateForFinancialYear(fy1,b335, b315, b326,b620, accountingPeriod, constantsForTaxYear(TaxYear(fy1)), differentUpperLimits = false)
    }
    B329(true)
    CATO05(roundedTwoDecimalPlaces(fy1Result + fy2Result))
  }

  private def calculateForFinancialYear(financialYear: Int,
                                        proRataProfitsChargeable: CtInteger,
                                        b315: B315,
                                        noOfCompanies: CtOptionalInteger,
                                        b620:B620,
                                        accountingPeriod: HmrcAccountingPeriod,
                                        constants: CtConstants,
                                        differentUpperLimits: Boolean): BigDecimal = {
    val daysInAccountingPeriod = daysBetween(accountingPeriod.start.value, accountingPeriod.end.value)

    val apDaysInFy = accountingPeriodDaysInFinancialYear(financialYear, accountingPeriod)

    val apFyRatio = apDaysInFy / daysInAccountingPeriod

    val thresholdTotalFyDays = if (differentUpperLimits) daysInFY(financialYear) else 365 max daysInAccountingPeriod
    val msFyRatio = apDaysInFy / thresholdTotalFyDays

    val apportionedProfit = (b315.value + b620) * apFyRatio

    val proRataLrma = (constants.lowerRelevantAmount * msFyRatio) / (noOfCompanies.orZero + 1)

    val proRataUrma = constants.upperRelevantAmount * msFyRatio / (noOfCompanies.orZero + 1)

    val mscrdueap = if (apportionedProfit > 0 &&
                        apportionedProfit > proRataLrma &&
                        apportionedProfit < (proRataUrma + BigDecimal("0.01"))) {
      (proRataUrma - apportionedProfit)*(proRataProfitsChargeable.value/apportionedProfit)* constants.reliefFraction
    } else {
      BigDecimal(0)
    }

    mscrdueap
  }

  def daysInFY(year: Int): Int = {
    val start = LocalDate.of(year, 4, 1)
    val end = start.plusYears(1).withMonth(3).withDayOfMonth(31)

    (start.until(end, ChronoUnit.DAYS) + 1).toInt
  }
}
