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

package uk.gov.hmrc.ct.ct600.v3.calculations

import uk.gov.hmrc.ct.box.{CtTypeConverters, EndDate, StartDate}
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper._
import uk.gov.hmrc.ct.ct600.calculations._
import uk.gov.hmrc.ct.ct600.v3._


trait CorporationTaxCalculator extends CtTypeConverters {
  /* since B475 is non-optional (always present), B510 will also always be present */
  def calculateTaxChargeable(b475: B475, b480: B480): B510 = {
    B510(b475.plus(b480.value.getOrElse(BigDecimal("0.0"))))
  }

  def calculateSATaxPayable(b510: B510, b515: B515): B525 = {
    B525(b510.minus(b515).max(0))
  }

  def calculateCorporationTax(b345: B345, b395: B395): B430 = {
    B430(b345.plus(b395))
  }

  def calculateTaxForFirstFinancialYear(b355: B335, b340: B340): B345 = {
    B345(b340.multiply(b355))
  }

  def rateOfTaxFy1(start: StartDate): BigDecimal = Ct600AnnualConstants.constantsForTaxYear(TaxYear(AccountingPeriodHelper.fallsInFinancialYear(start.value))).rateOfTax

  def rateOfTaxFy2(end: EndDate): BigDecimal = Ct600AnnualConstants.constantsForTaxYear(TaxYear(AccountingPeriodHelper.fallsInFinancialYear(end.value))).rateOfTax

  def financialYear1(accountingPeriod: HmrcAccountingPeriod): Int = {
    fallsInFinancialYear(accountingPeriod.start.value)
  }

  def financialYear2(accountingPeriod: HmrcAccountingPeriod): Option[Int] = {
    val fy2 = fallsInFinancialYear(accountingPeriod.end.value)
    if (financialYear1(accountingPeriod) != fy2) {
      Some(fy2)
    } else None
  }

  def calculateTaxForSecondFinancialYear(b385: B385, b390: B390) = {
    B395(b390.multiply(b385))
  }

  def calculateIncomeTaxRepayable(b515: B515, b510: B510) = {
    val calc = b515.minus(b510)
    B520(noneIfNegative(calc))
  }

  def calculateProfitsChargeableToCorporationTax(b235: B235, b275: B275): B300 = {
    B300(b235.minus(b275))
  }

  def calculateApportionedProfitsChargeableFy1(params: CorporationTaxCalculatorParameters): B335 = {
    B335(CorporationTaxHelper.calculateApportionedProfitsChargeableFy1(params))
  }

  def calculateApportionedProfitsChargeableFy2(params: CorporationTaxCalculatorParameters): B385 = {
    B385(CorporationTaxHelper.calculateApportionedProfitsChargeableFy2(params))
  }

  def calculateTotalTaxToPay(b525: B525, b595: B595): B600 = {
    val calc = b525.minus(b595)
    B600(noneIfNegative(calc))
  }

  def calculateSelfAssessmentOfTaxPayable(b525: B525, b527: B527): B528 = {
    val result = b525.plus(b527)
    B528(noneIfNegative(result))
  }

  def calculateTaxOverpaid(b595: B595, b525: B525): B605 = {
    val calc = b595.minus(b525)
    B605(noneIfNegative(calc))
  }

  private def noneIfNegative(calc: BigDecimal): Option[BigDecimal] = {
    if (calc < BigDecimal(0)) None else Some(calc)
  }

  def areAmountsCarriedBackFromLaterPeriods(cp286: CP286): B280 = cp286.value match {
    case Some(v) if v > 0 => B280(true)
    case _ => B280(false)
  }

  def defaultSetIfLossCarriedForward(b45input:B45Input, cp287:CP287): B45 = {
    if (cp287.orZero > 0) {
      B45(Some(true))
    } else {
      B45(b45input.value)
    }
  }
}
