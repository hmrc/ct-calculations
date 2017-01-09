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

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.CP2
import uk.gov.hmrc.ct.ct600.v2._
import uk.gov.hmrc.ct.ct600a.v2._

trait LoansToParticipatorsCalculator extends CtTypeConverters {

  def calculateLPQ01(lpq03: LPQ03, lpq04: LPQ04, lpq05: LPQ05): LPQ01 = {
    (lpq03.value, lpq04.value, lpq05.value) match {
      case (Some(true), Some(false), Some(true)) => LPQ01(true)
      case (Some(true), Some(true), _) => LPQ01(true)
      case _ => LPQ01(false)
    }
  }

  def calculateA2(lp02: LP02): A2 = {
    val allLoans: List[Loan] = lp02.loans.getOrElse(Nil)
    val sumOfLoanAmounts: Int = allLoans.flatMap(l => Some(l.amount)).sum
    A2(Some(sumOfLoanAmounts))
  }

  def calculateA3(a2: A2): A3 = {
    A3(a2.value.map(x => BigDecimal(x * 0.25)))
  }

  def calculateA4(cp2: CP2, lp02: LP02): A4 = {
    val validLoans: List[Loan] = lp02.loans.getOrElse(Nil).filter { loan =>
      loan.isRepaymentReliefEarlierThanDue(cp2.value)
    }
    if (validLoans.isEmpty) {
      A4(None)
    } else {
      val sumOfRepayments: Int = validLoans.flatMap(l => l.totalAmountRepaid).sum
      A4(Some(sumOfRepayments))
    }
  }


  def calculateA5(cp2: CP2, lp03: LP03): A5 = {
    val validWriteOffs: List[WriteOff] = lp03.writeOffs.getOrElse(Nil).filter { writeOff =>
      writeOff.isReliefEarlierThanDue(cp2.value)
    }
    if (validWriteOffs.isEmpty) {
      A5(None)
    } else {
      val writeOffs: Int = validWriteOffs.flatMap(w => Some(w.amountWrittenOff)).sum
      A5(Some(writeOffs))
    }
  }

  def calculateA6(a4: A4, a5: A5): A6 = {
    (a4, a5) match {
      case (A4(None), A5(None)) => A6(None)
      case _ => A6(Some(a4 plus a5))
    }
  }

  def calculateA7(a6: A6): A7 = {
    A7(a6.value.map(x => BigDecimal(x * 0.25)))
  }

  def calculateA8(cp2: CP2, lp02: LP02, filingDate: LPQ07): A8 = {
    val validLoans: List[Loan] = lp02.loans.getOrElse(Nil).filter { loan =>
      loan.isRepaymentLaterReliefNowDue(cp2.value, filingDate)
    }
    if(validLoans.isEmpty){
      A8(None)
    }else {
      val sumOfRepayments: Int = validLoans.flatMap(l => l.totalAmountRepaid).sum
      A8(Some(sumOfRepayments))
    }
  }

  def calculateA8Inverse(cp2: CP2, lp02: LP02, filingDate: LPQ07): A8Inverse = {
    val validLoans: List[Loan] = lp02.loans.getOrElse(Nil).filter { loan =>
      loan.isRepaymentLaterReliefNotYetDue(cp2.value, filingDate)
    }
    val sumOfRepayments: Int = validLoans.flatMap(l => l.totalAmountRepaid).sum
    A8Inverse(Some(sumOfRepayments))
  }

  def calculateA9(cp2: CP2, lp03: LP03, filingDate: LPQ07): A9 = {
    val validWriteOffs: List[WriteOff] = lp03.writeOffs.getOrElse(Nil).filter { writeOff =>
      writeOff.isLaterReliefNowDue(cp2.value, filingDate)
    }
    if(validWriteOffs.isEmpty){
      A9(None)
    }else {
      val writeOffs: Int = validWriteOffs.flatMap(w => Some(w.amountWrittenOff)).sum
      A9(Some(writeOffs))
    }
  }

  def calculateA9Inverse(cp2: CP2, lp03: LP03, filingDate: LPQ07): A9Inverse = {
    val validWriteOffs: List[WriteOff] = lp03.writeOffs.getOrElse(Nil).filter { writeOff =>
      writeOff.isLaterReliefNotYetDue(cp2.value, filingDate)
    }
    val writeOffs: Int = validWriteOffs.flatMap(w => Some(w.amountWrittenOff)).sum
    A9Inverse(Some(writeOffs))
  }

  def calculateA10(a8: A8, a9: A9): A10 = {
    (a8, a9) match {
      case (A8(None), A9(None)) => A10(None)
      case _ =>  A10(Some(a8 plus a9))
    }
  }

  def calculateA10Inverse(a8Inverse: A8Inverse, a9Inverse: A9Inverse): A10Inverse = {
    A10Inverse(Some(a8Inverse plus a9Inverse))
  }
  
  def calculateA11(a10: A10): A11 = {
    A11(a10.value.map(x => BigDecimal(x * 0.25)))
  }

  def calculateA11Inverse(a10Inverse: A10Inverse): A11Inverse = {
    A11Inverse(a10Inverse.value.map(x => BigDecimal(x * 0.25)))
  }

  def calculateA12(a2: A2, lp04: LP04): A12 = {
    A12(Some(a2 + lp04))
  }


  def calculateA13(a3: A3, a7: A7, a11: A11): A13 = {

    val reliefDueBeforeNineMonths = a7.value.getOrElse(BigDecimal(0))
    val reliefDueAfterNineMonths = a11.value.getOrElse(BigDecimal(0))

    A13 (
      a3.value.map { tax =>
        tax - reliefDueBeforeNineMonths - reliefDueAfterNineMonths
      }
    )
  }

  def calculateB80(a11: A11) = {
    a11.value match {
      case Some(x) if x > 0 =>  B80(Some(true))
      case _ => B80(None)
    }
  }
}
