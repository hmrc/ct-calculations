/*
 * Copyright 2015 HM Revenue & Customs
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

  def calculateLPQ01(lpq03: LPQ03, lpq04: LPQ04, lpq05: LPQ05): LPQ01 = {
    (lpq03.value, lpq04.value, lpq05.value) match {
      case (Some(true), Some(false), Some(true)) => LPQ01(true)
      case (Some(true), Some(true), _) => LPQ01(true)
      case _ => LPQ01(false)
    }
  }

  def calculateA15(a10: A10): A15 = {
    val sumOfLoanAmounts: Int = a10.loans.flatMap(l => Some(l.amount)).sum
    A15(Some(sumOfLoanAmounts))
  }

  def calculateA20(a15: A15): A20 = {
    A20(a15.value.map(x => BigDecimal(x * 0.25)))
  }

  def calculateA30(cp2: CP2, a10: A10): A30 = {
    val validLoans: List[Loan] = a10.loans.filter { loan =>
      loan.isRepaymentReliefEarlierThanDue(cp2.value)
    }
    if (validLoans.isEmpty) {
      A30(None)
    } else {
      val sumOfRepayments: Int = validLoans.flatMap(l => l.totalAmountRepaid).sum
      A30(Some(sumOfRepayments))
    }
  }


  def calculateA35(cp2: CP2, a25: A25): A35 = {
    val validWriteOffs: List[WriteOff] = a25.writeOffs.filter { writeOff =>
      writeOff.isReliefEarlierThanDue(cp2.value)
    }
    if (validWriteOffs.isEmpty) {
      A35(None)
    } else {
      val writeOffs: Int = validWriteOffs.flatMap(w => Some(w.amountWrittenOff)).sum
      A35(Some(writeOffs))
    }
  }

  def calculateA40(a30: A30, a35: A35): A40 = {
    (a30, a35) match {
      case (A30(None), A35(None)) => A40(None)
      case _ => A40(Some(a30 plus a35))
    }
  }

  def calculateA45(a40: A40): A45 = {
    A45(a40.value.map(x => BigDecimal(x * 0.25)))
  }

  def calculateA55(cp2: CP2, A10: A10, filingDate: LPQ07): A55 = {
    val validLoans: List[Loan] = A10.loans.filter { loan =>
      loan.isRepaymentLaterReliefNowDue(cp2.value, filingDate)
    }
    if(validLoans.isEmpty){
      A55(None)
    }else {
      val sumOfRepayments: Int = validLoans.flatMap(l => l.totalAmountRepaid).sum
      A55(Some(sumOfRepayments))
    }
  }

  def calculateA55Inverse(cp2: CP2, a10: A10, filingDate: LPQ07): A55Inverse = {
    val validLoans: List[Loan] = a10.loans.filter { loan =>
      loan.isRepaymentLaterReliefNotYetDue(cp2.value, filingDate)
    }
    val sumOfRepayments: Int = validLoans.flatMap(l => l.totalAmountRepaid).sum
    A55Inverse(Some(sumOfRepayments))
  }

  def calculateA60(cp2: CP2, a25: A25, filingDate: LPQ07): A60 = {
    val validWriteOffs: List[WriteOff] = a25.writeOffs.filter { writeOff =>
      writeOff.isLaterReliefNowDue(cp2.value, filingDate)
    }
    if(validWriteOffs.isEmpty){
      A60(None)
    }else {
      val writeOffs: Int = validWriteOffs.flatMap(w => Some(w.amountWrittenOff)).sum
      A60(Some(writeOffs))
    }
  }

  def calculateA60Inverse(cp2: CP2, a25: A25, filingDate: LPQ07): A60Inverse = {
    val validWriteOffs: List[WriteOff] = a25.writeOffs.filter { writeOff =>
      writeOff.isLaterReliefNotYetDue(cp2.value, filingDate)
    }
    val writeOffs: Int = validWriteOffs.flatMap(w => Some(w.amountWrittenOff)).sum
    A60Inverse(Some(writeOffs))
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

//  DO WE STILL NEED B80 BACK-FILLED IN THE CT600???
//  def calculateB80(a70: A70) = {
//    a70.value match {
//      case Some(x) if x > 0 =>  B80(Some(true))
//      case _ => B80(None)
//    }
//  }
}
