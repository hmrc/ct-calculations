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

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.CP2
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600a.v3._

trait LoansToParticipatorsCalculator extends CtTypeConverters {

  def calculateLPQ01(lpq03: LPQ03, lpq04: LPQ04): LPQ01 = {
    (lpq03.value, lpq04.value) match {
      case (Some(true), Some(true)) => LPQ01(true)
      case _ => LPQ01(false)
    }
  }

  // CHRIS cardinality 1..1 - cannot be null
  def calculateA15(loans2p: LoansToParticipators): A15 = {
    val sumOfLoanAmounts: Int = loans2p.loans.flatMap(l => Some(l.amount)).sum
    A15(Some(sumOfLoanAmounts))
  }

  // CHRIS cardinality 1..1 - cannot be null
  def calculateA20(a15: A15): A20 = {
    A20(a15.value.map(x => BigDecimal(x * 0.25)))
  }


  // CHRIS cardinality 0..1 - can be null
  def calculateA30(cp2: CP2, loans2p: LoansToParticipators): A30 = {
    val validLoans: List[Loan] = loans2p.loans.filter { loan =>
      loan.repaymentWithin9Months match {
        case Some(r: Repayment) if r.isReliefEarlierThanDue(cp2.value) => true
        case _ => false
      }
    }
    val sumOfRepayments: Int = validLoans.map(_.repaymentWithin9Months match {
      case Some(x: Repayment) => x.amount
      case _ => 0
    }).sum
    if (validLoans.isEmpty) A30(None) else A30(Some(sumOfRepayments))
  }

  // CHRIS cardinality is 0..1 - so can be null
  def calculateA35(cp2: CP2, loans2p: LoansToParticipators): A35 = {
    val validWriteOffs: List[WriteOff] = loans2p.loans.flatMap(loan =>
      loan.writeOffs.filter(writeOff =>
        writeOff.isReliefEarlierThanDue(cp2.value))
    )
    val writeOffs: Int = validWriteOffs.map(w => w.amount).sum
    if (validWriteOffs.isEmpty) A35(None) else A35(Some(writeOffs))
  }

  def calculateA40(a30: A30, a35: A35): A40 = {
    (a30, a35) match {
      case (A30(None), A35(None)) => A40(None)
      case _ => A40(Some(a30 plus a35))
    }
  }

  // CHRIS cardinality 1..1 - cannot be empty
  def calculateA45(a40: A40): A45 = {
    A45(a40.value.map(x => BigDecimal(x * 0.25)))
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
    A70(a65.value.map(x => BigDecimal(x * 0.25)))
  }

  def calculateA70Inverse(a65Inverse: A65Inverse): A70Inverse = {
    A70Inverse(a65Inverse.value.map(x => BigDecimal(x * 0.25)))
  }

  def calculateA75(a15: A15, lp04: LP04, a40: A40, a65: A65): A75 = {
    A75(Some(a15 + lp04 - a40 - a65))
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

}
