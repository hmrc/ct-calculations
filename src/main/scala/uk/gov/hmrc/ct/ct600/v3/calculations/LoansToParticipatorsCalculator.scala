/*
 * Copyright 2016 HM Revenue & Customs
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

trait LoansToParticipatorsCalculator extends CtTypeConverters {

  val LoansRateBeforeApril2016 = 0.25
  val LoansRateAfterApril2016 = 0.35
  
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
    val sumOfLoanAmounts: Int = (loans2p.loans.map(_.amount)).sum
    A15(Some(sumOfLoanAmounts))
  }

  // CHRIS cardinality 1..1 - cannot be null
  def calculateA20(a15: A15, loans2p: LoansToParticipators): A20 = {
    val amountsBeforeApril2016: Int = loans2p.loans.flatMap(_.amountBefore06042016).sum
    val value = (a15.value) match {
      case None => None
      case Some(amount) => Some(BigDecimal(amount - amountsBeforeApril2016) * LoansRateAfterApril2016 + BigDecimal(amountsBeforeApril2016) * LoansRateBeforeApril2016)
    }
    A20(value)
  }

  // CHRIS cardinality 0..1 - can be null
  def calculateA30(cp2: CP2, loans2p: LoansToParticipators): A30 = {
    val validLoans: List[Loan] = loansWithValidRepaymentsWihin9Months(loans2p.loans, cp2.value)
    val sumOfRepayments: Int = validLoans.map(_.repaymentWithin9Months.get.amount).sum
    if (validLoans.isEmpty) A30(None) else A30(Some(sumOfRepayments))
  }

  // CHRIS cardinality is 0..1 - so can be null
  def calculateA35(cp2: CP2, loans2p: LoansToParticipators): A35 = {
    val validWriteOffs = validWriteOffsWithin9Months(loans2p.loans, cp2.value)
    val writeOffs: Int = validWriteOffs.map(_.amount).sum
    if (validWriteOffs.isEmpty) A35(None) else A35(Some(writeOffs))
  }

  def calculateA40(a30: A30, a35: A35): A40 = {
    (a30, a35) match {
      case (A30(None), A35(None)) => A40(None)
      case _ => A40(Some(a30 plus a35))
    }
  }

  // CHRIS cardinality 1..1 - cannot be empty
  def calculateA45(a40: A40, loans2p: LoansToParticipators, cp2: CP2): A45 = {
    val validLoans: List[Loan] = loansWithValidRepaymentsWihin9Months(loans2p.loans, cp2.value)
    val repaymentsAmountsBeforeApril2016: List[Int] = for {
      loan<-validLoans
      repayment <- loan.repaymentWithin9Months
      amount <- repayment.amountBefore06042016
    } yield amount

    val validWriteOffs = validWriteOffsWithin9Months(loans2p.loans, cp2.value)
    val writeOffsBeforeApril2016: List[Int] = for {
      writeOff <- validWriteOffs
      amount <- writeOff.amountBefore06042016
    } yield amount

    val amountBeforeApril2016 = repaymentsAmountsBeforeApril2016.sum + writeOffsBeforeApril2016.sum

    val value = a40.value match {
      case None => None
      case Some(amount) => Some(BigDecimal(amount - amountBeforeApril2016) * LoansRateAfterApril2016 + BigDecimal(amountBeforeApril2016) * LoansRateBeforeApril2016)
    }
    A45(value)
  }

  // CHRIS cardinality 0..1 - can be null
  def calculateA55(cp2: CP2, loans2p: LoansToParticipators, filingDate: LPQ07): A55 = {
    val validRepayments: List[Repayment] = loans2p.loans.flatMap(_.otherRepayments.filter(_.isLaterReliefNowDue(cp2.value, filingDate)))
    if (validRepayments.isEmpty) A55(None) else A55(Some(validRepayments.foldLeft(0)(_ + _.amount)))
  }

  def calculateA55Inverse(apEndDate: CP2, loans2p: LoansToParticipators, filingDate: LPQ07): A55Inverse = {
    val validRepayments: List[Repayment] = loans2p.loans.flatMap(_.otherRepayments.filter(_.isLaterReliefNotYetDue(apEndDate.value, filingDate)))
    if (validRepayments.isEmpty) A55Inverse(None) else A55Inverse(Some(validRepayments.foldLeft(0)(_ + _.amount)))
  }

  //CHRIS cardinality 0..1 - can be null
  def calculateA60(cp2: CP2, loans2p: LoansToParticipators, filingDate: LPQ07): A60 = {
    val validWriteOffs: List[WriteOff] = loans2p.loans.flatMap(loan =>
      loan.writeOffs.filter(writeOff =>
        writeOff.isLaterReliefNowDue(cp2.value, filingDate))
    )
    if(validWriteOffs.isEmpty) A60(None) else A60(Some(validWriteOffs.flatMap(w => Some(w.amount)).sum))
  }

  def calculateA60Inverse(cp2: CP2, loans2p: LoansToParticipators, filingDate: LPQ07): A60Inverse = {
    val validWriteOffs: List[WriteOff] = loans2p.loans.flatMap(loan =>
      loan.writeOffs.filter(writeOff =>
        writeOff.isLaterReliefNotYetDue(cp2.value, filingDate))
    )
    if(validWriteOffs.isEmpty) A60Inverse(None) else A60Inverse(Some(validWriteOffs.flatMap(w => Some(w.amount)).sum))
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
  def calculateA70(a65: A65): A70 = {
    A70(a65.value.map(x => BigDecimal(x * LoansRateBeforeApril2016)))
  }

  def calculateA70Inverse(a65Inverse: A65Inverse): A70Inverse = {
    A70Inverse(a65Inverse.value.map(x => BigDecimal(x * LoansRateBeforeApril2016)))
  }

  def calculateA75(a15: A15, lp04: LP04): A75 = {
    A75(Some(a15 + lp04))
  }

  def calculateA80(a20: A20, a45: A45, a70: A70): A80 = {

    val reliefDueBeforeNineMonths = a45.value.getOrElse(BigDecimal(0))
    val reliefDueAfterNineMonths = a70.value.getOrElse(BigDecimal(0))

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

  private def loansWithValidRepaymentsWihin9Months(loans: List[Loan], date: LocalDate): List[Loan] = for {
    loan <- loans
    repayment <-loan.repaymentWithin9Months if repayment.isReliefEarlierThanDue(date)
  } yield loan

  private def validWriteOffsWithin9Months(loans: List[Loan], date: LocalDate): List[WriteOff] = {
    for {
      loan <- loans
      writeOff <- loan.writeOffs if writeOff.isReliefEarlierThanDue(date)
    } yield writeOff
  }

}
