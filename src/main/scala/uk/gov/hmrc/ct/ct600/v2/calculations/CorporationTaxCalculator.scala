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

package uk.gov.hmrc.ct.ct600.v2.calculations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600._
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper._
import uk.gov.hmrc.ct.ct600.calculations._
import uk.gov.hmrc.ct.ct600.v2._

trait CorporationTaxCalculator extends CtTypeConverters with NumberRounding {

  def financialYear1(accountingPeriod: HmrcAccountingPeriod): Int = {
    fallsInFinancialYear(accountingPeriod.start.value)
  }

  def financialYear2(accountingPeriod: HmrcAccountingPeriod): Option[Int] = {
    val fy2 = fallsInFinancialYear(accountingPeriod.end.value)
    if (financialYear1(accountingPeriod) != fy2) {
      Some(fy2)
    } else None
  }

  def rateOfTaxFy1(accountingPeriod: HmrcAccountingPeriod, b37: B37, b42: B42, b39: B39, b38: B38): BigDecimal = {
    calculateRateOfTaxYear(accountingPeriod.start.value, b37, b42, b39, b38)
  }

  def rateOfTaxFy2(accountingPeriod: HmrcAccountingPeriod, b37: B37, b42: B42, b39: B39, b38: B38): BigDecimal = {
    calculateRateOfTaxYear(accountingPeriod.end.value, b37, b42, b39, b38)
  }

  // smallCompaniesRateOfTax, rateOfTax,
  private def calculateRateOfTaxYear(date: LocalDate, b37: B37, b42: B42, b39: B39, b38: B38): BigDecimal = {
    val constantsForTaxYear = Ct600AnnualConstants.constantsForTaxYear(TaxYear(fallsInFinancialYear(date)))
    val rate = constantsForTaxYear.rateOfTax

    val taxable = b38 + b37
    if (taxable > Ct600AnnualConstants.lowProfitsThreshold(b39.value) || taxable <= 0) rate
    else if (b42.value) constantsForTaxYear.smallCompaniesRateOfTax
    else rate
  }

  def corporationTaxFy1(proRataProfitsChargeable: B44, rateOfTax: B45): B46 = {
    B46(BigDecimal(proRataProfitsChargeable) * rateOfTax.value)
  }

  def corporationTaxFy2(proRataProfitsChargeable: B54, rateOfTax: B55): B56 = {
    B56(BigDecimal(proRataProfitsChargeable) * rateOfTax.value)
  }

  def corporationTaxFy1RoundedHalfDown(corporationTaxFy1: B46): B46R = {
    B46R(roundedToIntHalfDown(corporationTaxFy1.value))
  }

  def corporationTaxFy2RoundedHalfUp(corporationTaxFy2: B56): B56R = {
    B56R(roundedToIntHalfUp(corporationTaxFy2.value))
  }

  def totalCorporationTaxChargeable(corporationTaxFy1: B46, corporationTaxFy2: B56): B63 = {
    B63(corporationTaxFy1 plus corporationTaxFy2)
  }

  def corporationTaxNetOfMrr(corporationTaxFy1: B46, corporationTaxFy2: B56, marginalReliefRate: B64): B65 = {
    B65((corporationTaxFy1 plus corporationTaxFy2) - marginalReliefRate.value)
  }

  def finalCorporationTaxChargeable(claimingMrr: B42, corporationTaxFy1: B46, corporationTaxFy2: B56, marginalReliefRate: B64): B70 = {
    if (claimingMrr.value) {
      B70(corporationTaxNetOfMrr(corporationTaxFy1, corporationTaxFy2, marginalReliefRate).value)
    } else {
      B70(totalCorporationTaxChargeable(corporationTaxFy1, corporationTaxFy2).value)
    }
  }

  def calculateApportionedProfitsChargeableFy1(params: CorporationTaxCalculatorParameters): B44 = {
    B44(CorporationTaxHelper.calculateApportionedProfitsChargeableFy1(params))
  }

  def calculateApportionedProfitsChargeableFy2(params: CorporationTaxCalculatorParameters): B54 = {
    B54(CorporationTaxHelper.calculateApportionedProfitsChargeableFy2(params))
  }
}
