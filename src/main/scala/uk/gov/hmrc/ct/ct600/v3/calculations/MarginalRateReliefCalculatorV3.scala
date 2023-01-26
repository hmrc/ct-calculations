/*
 * Copyright 2023 HM Revenue & Customs
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

import uk.gov.hmrc.ct.{CATO04, CATO05}
import uk.gov.hmrc.ct.box.{CtInteger, CtOptionalInteger, CtTypeConverters}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.NumberRounding
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper._
import uk.gov.hmrc.ct.ct600.calculations.Ct600AnnualConstants._
import uk.gov.hmrc.ct.ct600.calculations.{CtConstants, TaxYear}
import uk.gov.hmrc.ct.ct600.v2._
import uk.gov.hmrc.ct.ct600.v3.{B315, B326, B327, B328, B335, B385}


trait MarginalRateReliefCalculatorV3 extends CtTypeConverters with NumberRounding {

  def computeMarginalRateReliefV3(b315: B315, b335: B335, b385: B385, b326: B326,b327:B327,b328:B328, accountingPeriod: HmrcAccountingPeriod): CATO05 = {
    validateAccountingPeriod(accountingPeriod)

    val fy1: Int = startingFinancialYear(accountingPeriod.start)
    val fy2: Int = endingFinancialYear(accountingPeriod.end)
    var fy1Result=BigDecimal(0);
    var fy2Result=BigDecimal(0);

    if (fy2 != fy1){
      fy1Result = calculateForFinancialYear(fy1,b335, b315, b327, accountingPeriod, constantsForTaxYear(TaxYear(fy1)))
       fy2Result =calculateForFinancialYear(fy2,b385, b315, b328, accountingPeriod, constantsForTaxYear(TaxYear(fy2)))
    }
    else{
      fy1Result = calculateForFinancialYear(fy1,b335, b315, b326, accountingPeriod, constantsForTaxYear(TaxYear(fy1)))
    }

    CATO05(roundedTwoDecimalPlaces(fy1Result + fy2Result))
  }

  private def calculateForFinancialYear(financialYear: Int,
                                        proRataProfitsChargeable: CtInteger,
                                        b315: B315,
                                        noOfCompanies: CtOptionalInteger,
                                        accountingPeriod: HmrcAccountingPeriod,
                                        constants: CtConstants): BigDecimal = {
    val daysInAccountingPeriod = daysBetween(accountingPeriod.start.value, accountingPeriod.end.value)

    val apDaysInFy = accountingPeriodDaysInFinancialYear(financialYear, accountingPeriod)

    val apFyRatio = apDaysInFy / daysInAccountingPeriod

    val msFyRatio = apDaysInFy / (365 max daysInAccountingPeriod)

    val apportionedProfit = b315.value * apFyRatio

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

}
