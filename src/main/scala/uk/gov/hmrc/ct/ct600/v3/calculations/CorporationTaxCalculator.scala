/*
 * Copyright 2021 HM Revenue & Customs
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

  def calculateCorportationTaxForNIJourney(b345: B345, b360: B360, b395: B395, b410: B410): B430 = {
    B430(b345.plus(b360.plus(b395.plus(b410))))
  }

  def calculateTaxForFirstFinancialYear(b335: B335, b340: B340): B345 = {
    B345(b340.multiply(b335))
  }

  def rateOfTaxFy1(start: StartDate): BigDecimal = {
    Ct600AnnualConstants.getConstantsFromYear(
      startingFinancialYear(start)
    ).rateOfTax
  }

  def rateOfTaxFy2(end: EndDate): BigDecimal = {
    Ct600AnnualConstants.getConstantsFromYear(
      endingFinancialYear(end)
    ).rateOfTax
  }

  def financialYear1(accountingPeriod: HmrcAccountingPeriod): Int = {
    startingFinancialYear(accountingPeriod.start)
  }

  def financialYear2(accountingPeriod: HmrcAccountingPeriod): Option[Int] = {
    val fy2 = endingFinancialYear(accountingPeriod.end)
    if (financialYear1(accountingPeriod) != fy2) {
      Some(fy2)
    } else None
  }

  def calculateTaxForSecondFinancialYear(b385: B385, b390: B390): B395 = {
    B395(b390.multiply(b385))
  }

  def calculateIncomeTaxRepayable(b515: B515, b510: B510): B520 = {
    val calc = b515.minus(b510)
    B520(noneIfNegative(calc))
  }

  def calculateProfitsChargeableToCorporationTax(b235: B235, b275: B275, b285: B285): B300 = {
    B300(b235.minus(b275.value + b285.value))
  }

  def calculateApportionedProfitsChargeableFy1(params: CorporationTaxCalculatorParameters): B335 = {
    B335(HmrcValueApportioning.calculateApportionedProfitsChargeableFy1(params))
  }

  def calculateApportionedProfitsChargeableFy2(params: CorporationTaxCalculatorParameters): B385 = {
    B385(HmrcValueApportioning.calculateApportionedProfitsChargeableFy2(params))
  }

  def calculateNIApportionedNonTradingProfitsChargeableFy1(params: NINonTradingProfitCalculationParameters): B335 = {
    B335(HmrcValueApportioning.calculateNIApportionedNonTradingProfitsChargeableFy1(params))
  }

  def calculateNIApportionedNonTradingProfitsChargeableFy2(params: NINonTradingProfitCalculationParameters): B385 = {
    B385(HmrcValueApportioning.calculateNIApportionedNonTradingProfitsChargeableFy2(params))
  }

  def calculateTotalTaxToPay(b525: B525, b595: B595): B600 = {
    val calc = b525.minus(b595)
    B600(noneIfNegative(calc))
  }

  def calculateSelfAssessmentOfTaxPayable(b525: B525, b526: B526, b527: B527): B528 = {
    val result = b525.plus(b527.plus(b526))
    B528(noneIfNegative(result))
  }

  def calculateTaxOverpaid(b595: B595, b525: B525): B605 = {
    val calc = b595.minus(b525)
    B605(noneIfNegative(calc))
  }

  def calculateJrbEothoOverpayments(b476: B476, b477: B477): B474 = {
    val calc = b476.plus(b477)
    B474(noneIfNeg(calc))
  }

  private def noneIfNegative(calc: BigDecimal): Option[BigDecimal] = {
    if (calc < BigDecimal(0)) None else Some(calc)
  }

  private def noneIfNeg(calc: Int): Option[Int] = {
    if (calc < BigDecimal(0)) None else Some(calc)
  }

  def areAmountsCarriedBackFromLaterPeriods(cp286: CP286): B280 = cp286.value match {
    case Some(v) if v > 0 => B280(true)
    case _ => B280(false)
  }

  def defaultSetIfLossCarriedForward(b45input: B45Input, cp287: CP287): B45 = {
    if (cp287.orZero > 0) {
      B45(Some(true))
    } else {
      B45(b45input.value)
    }
  }
}
