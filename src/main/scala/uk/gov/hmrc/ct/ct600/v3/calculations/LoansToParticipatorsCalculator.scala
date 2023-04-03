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

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.CP2
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600a.v3._
import uk.gov.hmrc.ct.utils.DateImplicits._

trait LoansToParticipatorsCalculator extends CtTypeConverters {

  val LoansRateBeforeApril2016: Double = 0.25
  val LoansRateBetweenApril2016TO2022OrAfterApril2023: Double = 0.325
  val LoansRateAfterApril2022: Double = 0.3375
  val DateOF2016TaxRateForLoans: LocalDate = new LocalDate(2016, 4, 6)
  val DateOF2022TaxRateForLoans: LocalDate = new LocalDate(2022, 4, 6)
  val DateOf2023TaxRateForLoans: LocalDate = new LocalDate(2023, 4, 6)
  def calculateLPQ01(lpq04: LPQ04, lpq10: LPQ10, a5: A5, lpq03: LPQ03): LPQ01 = {
    (lpq04.value, lpq10.value, a5.value, lpq03.value) match {
      case (Some(true), Some(true), _, _) => LPQ01(true)
      case (Some(true), _, Some(true), _) => LPQ01(true)
      case (Some(true), _, _, Some(true)) => LPQ01(true)
      case _ => LPQ01(false)
    }
  }

  // CHRIS cardinality 1..1 - cannot be null
  def calculateA15(loans2p: LoansToParticipators): A15 = {
    val sumOfLoanAmounts: Int = loans2p.loans.map(_.amount.getOrElse(0)).sum
    A15(Some(sumOfLoanAmounts))
  }

  // CHRIS cardinality 1..1 - cannot be null
  def calculateA20(a15: A15, loans2p: LoansToParticipators, cp2: CP2): A20 = {
    val value = if(shouldApply2023TaxRateForLoans(cp2)) {
      amountAtNewTaxRate(a15.value, loans2p.loans.flatMap(_.amountBetween06042022To06042023).sum)
    } else if (shouldApply2022TaxRateForLoans(cp2)) {
      amountAt2022TaxRate(a15.value, loans2p.loans.flatMap(_.amountBetween06042016To06042022).sum)
    } else if (shouldApply2016TaxRateForLoans(cp2)) {
      amountAt2016To2022TaxRate(a15.value, loans2p.loans.flatMap(_.amountBefore06042016).sum)
    } else {
      amountAtOldTaxRate(a15.value)
    }

    A20(value)
  }

  // CHRIS cardinality 0..1 - can be null
  def calculateA30(cp2: CP2, loans2p: LoansToParticipators): A30 = {
    val validRepayments: List[Repayment] = validRepaymentsWithin9Months(loans2p.loans, cp2.value)
    val sumOfRepayments: Int = validRepayments.map(_.amount.getOrElse(0)).sum
    if (validRepayments.isEmpty) A30(None) else A30(Some(sumOfRepayments))
  }

  // CHRIS cardinality is 0..1 - so can be null
  def calculateA35(cp2: CP2, loans2p: LoansToParticipators): A35 = {
    val validWriteOffs = validWriteOffsWithin9Months(loans2p.loans, cp2.value)
    val sumOfWriteOffs: Int = validWriteOffs.map(_.amount.getOrElse(0)).sum
    if (validWriteOffs.isEmpty) A35(None) else A35(Some(sumOfWriteOffs))
  }

  def calculateA40(a30: A30, a35: A35): A40 = {
    (a30, a35) match {
      case (A30(None), A35(None)) => A40(None)
      case _ => A40(Some(a30 plus a35))
    }
  }

  // CHRIS cardinality 1..1 - cannot be empty
  def calculateA45(a40: A40, loans2p: LoansToParticipators, cp2: CP2): A45 = {
    val value =
      if(shouldApply2022TaxRateForLoans(cp2)||shouldApply2016TaxRateForLoans(cp2) || shouldApply2023TaxRateForLoans(cp2)) {

        val validRepayments: List[Repayment] = validRepaymentsWithin9Months(loans2p.loans, cp2.value)
        val validWriteOffs: List[WriteOff] = validWriteOffsWithin9Months(loans2p.loans, cp2.value)

        if (shouldApply2023TaxRateForLoans(cp2)) {
          val amountOfA40BeforeApril2023 = totalAmountBeforeApril2023(validRepayments, validWriteOffs)
          amountAtNewTaxRate(a40.value, amountOfA40BeforeApril2023)
        } else if(shouldApply2022TaxRateForLoans(cp2)) {
          val amountOfA40BeforeApril2022 = totalAmountBeforeApril2022(validRepayments,validWriteOffs)
          amountAt2022TaxRate(a40.value, amountOfA40BeforeApril2022)
        } else {
          val amountOfA40InverseBeforeApril2016=totalAmountBeforeApril2016(validRepayments,validWriteOffs)
          amountAt2016To2022TaxRate(a40.value, amountOfA40InverseBeforeApril2016)
        }
      } else amountAtOldTaxRate(a40.value)

    A45(value)
  }

  // CHRIS cardinality 0..1 - can be null
  def calculateA55(cp2: CP2, loans2p: LoansToParticipators, filingDate: LPQ07): A55 = {
    val validRepayments: List[Repayment] = validRepaymentsWithLaterReliefNowDue(loans2p.loans, cp2.value, filingDate)
    val sumOfRepayments: Int = validRepayments.map(_.amount.getOrElse(0)).sum
    if (validRepayments.isEmpty) A55(None) else A55(Some(sumOfRepayments))
  }

  def calculateA55Inverse(apEndDate: CP2, loans2p: LoansToParticipators, filingDate: LPQ07): A55Inverse = {
    val validRepayments: List[Repayment] = validRepaymentsWithLaterReliefNotYetDue(loans2p.loans, apEndDate.value, filingDate)
    val sumOfRepayments: Int = validRepayments.map(_.amount.getOrElse(0)).sum
    if (validRepayments.isEmpty) A55Inverse(None) else A55Inverse(Some(sumOfRepayments))
  }

  //CHRIS cardinality 0..1 - can be null
  def calculateA60(cp2: CP2, loans2p: LoansToParticipators, filingDate: LPQ07): A60 = {
    val validWriteOffs: List[WriteOff] = vailidWriteOffWithLaterReliefNowDue(loans2p.loans, cp2.value, filingDate)
    val sumOfWriteOffs: Int = validWriteOffs.map(_.amount.getOrElse(0)).sum
    if(validWriteOffs.isEmpty) A60(None) else A60(Some(sumOfWriteOffs))
  }

  def calculateA60Inverse(cp2: CP2, loans2p: LoansToParticipators, filingDate: LPQ07): A60Inverse = {
    val validWriteOffs: List[WriteOff] = vailidWriteOffWithLaterReliefNotYetDue(loans2p.loans, cp2.value, filingDate)
    val sumOfWriteOffs: Int = validWriteOffs.map(_.amount.getOrElse(0)).sum
    if(validWriteOffs.isEmpty) A60Inverse(None) else A60Inverse(Some(sumOfWriteOffs))
  }

  def calculateA65(a55: A55, a60: A60): A65 = {
    (a55, a60) match {
      case (A55(None), A60(None)) => A65(None)
      case _ =>  A65(Some(a55 plus a60))
    }
  }

  def calculateA65Inverse(a55Inverse: A55Inverse, a60Inverse: A60Inverse): A65Inverse = {
    A65Inverse(Some(a55Inverse plus a60Inverse))
  }

  // CHRIS cardinality 1..1 - cannot be null
  def calculateA70(a65: A65, loans2p: LoansToParticipators, cp2: CP2, filingDate: LPQ07): A70 = {
    val value =
      if(shouldApply2022TaxRateForLoans(cp2)||shouldApply2016TaxRateForLoans(cp2) || shouldApply2023TaxRateForLoans(cp2)) {
        val validRepayments: List[Repayment] = validRepaymentsWithLaterReliefNowDue(loans2p.loans, cp2.value, filingDate)
        val validWriteOffs: List[WriteOff] = vailidWriteOffWithLaterReliefNowDue(loans2p.loans, cp2.value, filingDate)

        if (shouldApply2023TaxRateForLoans(cp2)) {
          val amountOfA65BeforeApril2023 = totalAmountBeforeApril2023(validRepayments, validWriteOffs)
          amountAtNewTaxRate(a65.value, amountOfA65BeforeApril2023)
        } else if(shouldApply2022TaxRateForLoans(cp2)) {
          val amountOfA65BeforeApril2022 = totalAmountBeforeApril2022(validRepayments,validWriteOffs)
          amountAt2022TaxRate(a65.value, amountOfA65BeforeApril2022)
        } else {
          val amountOfA65BeforeApril2016=totalAmountBeforeApril2016(validRepayments,validWriteOffs)
          amountAt2016To2022TaxRate(a65.value, amountOfA65BeforeApril2016)
        }
      } else amountAtOldTaxRate(a65.value)

    A70(value)
  }

  def calculateA70Inverse(a65Inverse: A65Inverse, loans2p: LoansToParticipators, cp2: CP2, filingDate: LPQ07): A70Inverse = {
    val value =
      if(shouldApply2022TaxRateForLoans(cp2)||shouldApply2016TaxRateForLoans(cp2) || shouldApply2023TaxRateForLoans(cp2))  {
        val validRepayments: List[Repayment] = validRepaymentsWithLaterReliefNotYetDue(loans2p.loans, cp2.value, filingDate)
        val validWriteOffs: List[WriteOff] = vailidWriteOffWithLaterReliefNotYetDue(loans2p.loans, cp2.value, filingDate)

        if (shouldApply2023TaxRateForLoans(cp2)) {
          val amountOfA65InverseBeforeApril2023 = totalAmountBeforeApril2023(validRepayments, validWriteOffs)
          amountAtNewTaxRate(a65Inverse.value, amountOfA65InverseBeforeApril2023)
        } else if(shouldApply2022TaxRateForLoans(cp2)) {
          val amountOfA65InverseBeforeApril2022 = totalAmountBeforeApril2022(validRepayments,validWriteOffs)
          amountAt2022TaxRate(a65Inverse.value, amountOfA65InverseBeforeApril2022)
        }  else {
          val amountOfA65InverseBeforeApril2016=totalAmountBeforeApril2016(validRepayments,validWriteOffs)
          amountAt2016To2022TaxRate(a65Inverse.value, amountOfA65InverseBeforeApril2016)
        }
      } else amountAtOldTaxRate(a65Inverse.value)

    A70Inverse(value)
  }

  def calculateA75(a15: A15, lp04: LP04): A75 = {
    A75(Some(a15 + lp04))
  }

  def calculateA80(a20: A20, a45: A45, a70: A70): A80 = {

    val reliefDueBeforeNineMonths = a45.value.getOrElse(BigDecimal(0)).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    val reliefDueAfterNineMonths = a70.value.getOrElse(BigDecimal(0)).setScale(2, BigDecimal.RoundingMode.HALF_UP)

    A80 (
      a20.value.map { tax =>
        tax - reliefDueBeforeNineMonths - reliefDueAfterNineMonths
      }
    )
  }

  def calculateB485(a70: A70): B485 = a70.value match {
    case Some(x) if x > 0 => B485(true)
    case _ => B485(false)
  }

  private def validRepaymentsWithin9Months(loans: List[Loan], date: LocalDate): List[Repayment] =
    for {
      loan <- loans
      repayment <- loan.repaymentWithin9Months
      if repayment.isReliefEarlierThanDue(date)
    } yield repayment

  private def validWriteOffsWithin9Months(loans: List[Loan], date: LocalDate): List[WriteOff] =
    for {
      loan <- loans
      writeOff <- loan.writeOffs
      if writeOff.isReliefEarlierThanDue(date)
    } yield writeOff

  private def validRepaymentsWithLaterReliefNowDue(loans: List[Loan], apEndDate: LocalDate, filingDate: LPQ07): List[Repayment] =
    for{
      loan <- loans
      repayment <- loan.otherRepayments
      if repayment.isLaterReliefNowDue(apEndDate, filingDate)
    } yield repayment

  private def validRepaymentsWithLaterReliefNotYetDue(loans: List[Loan], apEndDate: LocalDate, filingDate: LPQ07): List[Repayment] =
    for{
      loan <- loans
      repayment <- loan.otherRepayments
      if repayment.isLaterReliefNotYetDue(apEndDate, filingDate)
    } yield repayment

  private def vailidWriteOffWithLaterReliefNowDue(loans: List[Loan], apEndDate: LocalDate, filingDate: LPQ07): List[WriteOff] =
    for {
      loan <- loans
      writeOff <- loan.writeOffs
      if writeOff.isLaterReliefNowDue(apEndDate, filingDate)
    } yield writeOff

  private def vailidWriteOffWithLaterReliefNotYetDue(loans: List[Loan], apEndDate: LocalDate, filingDate: LPQ07): List[WriteOff] =
    for {
      loan <- loans
      writeOff <- loan.writeOffs
      if writeOff.isLaterReliefNotYetDue(apEndDate, filingDate)
    } yield writeOff

  private def amountAtOldTaxRate(amountOpt: Option[Int]) = {
    amountOpt match {
      case Some(amount) => Some(BigDecimal(amount) * LoansRateBeforeApril2016)
      case None => None
    }
  }


  private def shouldApply2016TaxRateForLoans(cp2: CP2): Boolean = cp2.value >= DateOF2016TaxRateForLoans && cp2.value < DateOF2022TaxRateForLoans

  private def shouldApply2022TaxRateForLoans(cp2: CP2): Boolean = cp2.value >= DateOF2022TaxRateForLoans && cp2.value < DateOf2023TaxRateForLoans

  private def shouldApply2023TaxRateForLoans(cp2: CP2): Boolean = cp2.value >= DateOf2023TaxRateForLoans


  private def amountAt2016To2022TaxRate(amountOpt: Option[Int], amountsBeforeApril2016: Int) = {
    amountOpt match {
      case Some(amount) =>
        val beforeApril2016 = BigDecimal(amount - amountsBeforeApril2016) * LoansRateBetweenApril2016TO2022OrAfterApril2023
        val afterApril2016 = BigDecimal(amountsBeforeApril2016) * LoansRateBeforeApril2016
        Some(beforeApril2016.setScale(2, BigDecimal.RoundingMode.HALF_UP) + afterApril2016.setScale(2, BigDecimal.RoundingMode.HALF_UP))
      case None => None
    }
  }

  private def amountAt2022TaxRate(amountOpt: Option[Int], amountsBeforeApril2022: Int) = {
    amountOpt match {
      case Some(amount) =>
        val afterApril2022 = BigDecimal(amount - amountsBeforeApril2022) * LoansRateAfterApril2022
        val beforeApril2022 = BigDecimal(amountsBeforeApril2022) * LoansRateBetweenApril2016TO2022OrAfterApril2023
        Some(beforeApril2022.setScale(2, BigDecimal.RoundingMode.HALF_UP) + afterApril2022.setScale(2, BigDecimal.RoundingMode.HALF_UP))
      case None => None
    }
  }
  private def amountAtNewTaxRate(amountOpt: Option[Int], amountsBeforeApril2023: Int) = {
    amountOpt match {
      case Some(amount) =>
        val afterApril2023 = BigDecimal(amount - amountsBeforeApril2023) * LoansRateBetweenApril2016TO2022OrAfterApril2023
        val beforeApril2023 = BigDecimal(amountsBeforeApril2023) * LoansRateAfterApril2022
        Some(beforeApril2023.setScale(2, BigDecimal.RoundingMode.HALF_UP) + afterApril2023.setScale(2, BigDecimal.RoundingMode.HALF_UP))
      case None => None
    }
  }

  private def totalAmountBeforeApril2016(validRepayments: List[Repayment], validWriteOffs: List[WriteOff]): Int ={

    val sumOfRepaymentsBeforeApril2016 = validRepayments.flatMap(_.amountBefore06042016).sum

    val sumOflWriteOffsBeforeApril2016 = validWriteOffs.flatMap(_.amountBefore06042016).sum

    sumOfRepaymentsBeforeApril2016 + sumOflWriteOffsBeforeApril2016

  }

  private def totalAmountBeforeApril2022(validRepayments: List[Repayment],validWriteOffs: List[WriteOff]): Int ={

    val sumOfRepaymentsBeforeApril2022 = validRepayments.flatMap(_.amountBetween06042016To06042022).sum

    val sumOflWriteOffsBeforeApril2022 = validWriteOffs.flatMap(_.amountBetween06042016To06042022).sum

    sumOfRepaymentsBeforeApril2022 + sumOflWriteOffsBeforeApril2022

  }

  private def totalAmountBeforeApril2023(validRepayments: List[Repayment], validWriteOffs: List[WriteOff]): Int = {

    val sumOfRepaymentsBeforeApril2023 = validRepayments.flatMap(_.amountBetween06042022To06042023).sum

    val sumOflWriteOffsBeforeApril2023 = validWriteOffs.flatMap(_.amountBetween06042022To06042023).sum

    sumOfRepaymentsBeforeApril2023 + sumOflWriteOffsBeforeApril2023
  }

}
