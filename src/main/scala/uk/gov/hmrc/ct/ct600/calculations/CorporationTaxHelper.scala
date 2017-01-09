/*
 * Copyright 2017 HM Revenue & Customs
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
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper._
import uk.gov.hmrc.ct.ct600.calculations.Ct600AnnualConstants._


object CorporationTaxHelper extends CtTypeConverters with NumberRounding {

  def calculateApportionedProfitsChargeableFy1(params: CorporationTaxCalculatorParameters): Int = {
    validateAccountingPeriod(params.accountingPeriod)

    val fy1: Int = fallsInFinancialYear(params.accountingPeriod.start.value)
    val fy1Result = calculateApportionedProfitsChargeableForYear(fy1, params, constantsForTaxYear(TaxYear(fy1)))

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

  private def calculateApportionedProfitsChargeableForYear(year: Int, params: CorporationTaxCalculatorParameters, constants: CtConstants): BigDecimal = {
    val profitsChargeable = BigDecimal(params.profitsChargeableToCT.value)
    val apDaysInFy = accountingPeriodDaysInFinancialYear(year, params.accountingPeriod)

    val apFyRatio = apDaysInFy / daysInAccountingPeriod(params.accountingPeriod)

    val proRataProfitsChargeable = profitsChargeable * apFyRatio

    proRataProfitsChargeable
  }
}

case class CorporationTaxCalculatorParameters(profitsChargeableToCT: CP295,
                                              accountingPeriod: HmrcAccountingPeriod)
