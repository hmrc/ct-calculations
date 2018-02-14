/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP295, HmrcAccountingPeriod}
import uk.gov.hmrc.ct.ct600.NumberRounding

object HmrcValueApportioning extends HmrcValueApportioning

trait HmrcValueApportioning extends CtTypeConverters with NumberRounding with AccountingPeriodHelper {

  def calculateApportionedProfitsChargeableFy1(params: CorporationTaxCalculatorParameters): Int = {
    validateAccountingPeriod(params.accountingPeriod)

    val fy1: Int = startingFinancialYear(params.accountingPeriod.start)
    val fy1Result = calculateApportionedProfitsChargeableForYear(fy1, params)

    roundedToIntHalfUp(fy1Result)
  }

  def calculateApportionedProfitsChargeableFy2(params: CorporationTaxCalculatorParameters): Int = {
    validateAccountingPeriod(params.accountingPeriod)

    if (accountingPeriodSpansTwoFinancialYears(params.accountingPeriod)) {
      params.profitsChargeableToCT - calculateApportionedProfitsChargeableFy1(params)
    } else {
      0
    }
  }

  private def calculateApportionedProfitsChargeableForYear(year: Int, params: CorporationTaxCalculatorParameters): BigDecimal = {
    calculateApportionedValueForYear(year, params.profitsChargeableToCT.value, params.accountingPeriod)
  }

  def calculateApportionedValuesForAccountingPeriod(valueBeingApportioned: Int, accountingPeriod: HmrcAccountingPeriod): Map[TaxYear, Int] = {
    validateAccountingPeriod(accountingPeriod)

    val fy1: Int = startingFinancialYear(accountingPeriod.start)
    val fy2: Int = endingFinancialYear(accountingPeriod.end)

    val firstYearValue = roundedToIntHalfUp(calculateApportionedValueForYear(fy1, valueBeingApportioned, accountingPeriod))

    if (fy1 != fy2)
      Map(TaxYear(fy1) -> firstYearValue, TaxYear(fy2) -> (valueBeingApportioned - firstYearValue))
    else
      Map(TaxYear(fy1) -> firstYearValue)
  }

  def calculateApportionedValueForYear(financialYear: Int, valueBeingApportioned: Int, accountingPeriod: HmrcAccountingPeriod): BigDecimal = {
    val valueAsDecimal = BigDecimal.valueOf(valueBeingApportioned)
    val apDaysInFy = accountingPeriodDaysInFinancialYear(financialYear, accountingPeriod)

    val apFyRatio = apDaysInFy / daysInAccountingPeriod(accountingPeriod)

    valueAsDecimal * apFyRatio
  }
}

case class CorporationTaxCalculatorParameters(profitsChargeableToCT: CP295,
                                              accountingPeriod: HmrcAccountingPeriod)
