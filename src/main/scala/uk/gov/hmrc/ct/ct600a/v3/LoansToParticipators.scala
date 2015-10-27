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

package uk.gov.hmrc.ct.ct600a.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtValue}
import uk.gov.hmrc.ct.ct600a.v3.formats.LoansFormatter


case class LoansToParticipators(loans: List[Loan] = List.empty) extends CtBoxIdentifier(name = "Loans to participators.") with CtValue[List[Loan]] {

  def +(other: LoansToParticipators): LoansToParticipators = new LoansToParticipators(loans ++ other.loans)

  override def value = loans

  override def asBoxString = LoansFormatter.asBoxString(this)
}


/**
 * Note: This Loan class uses the 'name' attribite as a primary key because that is what the CHRIS validation does.
 * @param endDateOfRepaymentAP :  The end date of the accounting period in which the loan repayment was made
 */
case class Loan (name: String,
                 amount: Int,
                 repaid: Option[Boolean] = None,
                 lastRepaymentDate: Option[LocalDate] = None,
                 totalAmountRepaid: Option[Int] = None,
                 endDateOfRepaymentAP: Option[LocalDate] = None,
                 hasWriteOffs: Option[Boolean] = None,
                 writeOffs: List[WriteOff] = List.empty) {

  def isRepaymentReliefEarlierThanDue(acctPeriodEnd: LocalDate): Boolean = {
    val nineMonthsAndADayAfter: LocalDate = acctPeriodEnd.plusMonths(9).plusDays(1)
    repaid.getOrElse(false) && lastRepaymentIsAfter(acctPeriodEnd) && lastRepaymentBefore(nineMonthsAndADayAfter)
  }

  def isRepaymentLaterReliefNowDue(acctPeriodEnd: LocalDate, filingDate: LPQ07): Boolean = {
    repaid.getOrElse(false) && !isRepaymentReliefEarlierThanDue(acctPeriodEnd) && isFilingAfterLaterReliefDueDate(filingDate)
  }

  def isRepaymentLaterReliefNotYetDue(acctPeriodEnd: LocalDate, filingDate: LPQ07): Boolean = {
    repaid.getOrElse(false) && !isRepaymentReliefEarlierThanDue(acctPeriodEnd) && !isFilingAfterLaterReliefDueDate(filingDate)
  }

  val isFullyRepaid = {
    repaid.getOrElse(false) && amount == totalAmountRepaid.getOrElse(0)
  }

  private def isFilingAfterLaterReliefDueDate(filingDate: LPQ07) =  endDateOfRepaymentAP match {
    case Some(date) => {
      val reliefDueDate = date.plusMonths(9)
      filingDate.value.map(_.isAfter(reliefDueDate)).getOrElse(false)  // LPQ07 None implies that filing is within 9 months of AP end date
    }
    case None => throw new IllegalArgumentException("As the repayment date is more than 9 months after the accounting period end date, the end date of the repayment accounting period must be provided")
  }


  private def lastRepaymentIsAfter(date: LocalDate) = lastRepaymentDate.fold(false)(x => x.isAfter(date))

  private def lastRepaymentBefore(date: LocalDate) = lastRepaymentDate.fold(false)(x => x.isBefore(date))


}

case class WriteOff(loanId: String, amountWrittenOff: Int, dateWrittenOff: LocalDate, endDateOfWriteOffAP : Option[LocalDate] = None) {

  def isReliefEarlierThanDue(acctPeriodEnd: LocalDate): Boolean = {
    requireEndDateOfWriteOffApp(acctPeriodEnd)
    val nineMonthsAndADayAfter: LocalDate = acctPeriodEnd.plusMonths(9).plusDays(1)
    dateWrittenOff.isAfter(acctPeriodEnd) && dateWrittenOff.isBefore(nineMonthsAndADayAfter)
  }

  def isLaterReliefNowDue(acctPeriodEnd: LocalDate, filingDate: LPQ07): Boolean = {
    requireEndDateOfWriteOffApp(acctPeriodEnd)
    !isReliefEarlierThanDue(acctPeriodEnd) && isFilingAfterLaterReliefDueDate(filingDate)
  }


  def isLaterReliefNotYetDue(acctPeriodEnd: LocalDate, filingDate: LPQ07): Boolean = {
    requireEndDateOfWriteOffApp(acctPeriodEnd)
    !isReliefEarlierThanDue(acctPeriodEnd) && isFilingBeforeReliefDueDate(filingDate)
  }

  private def isFilingAfterLaterReliefDueDate(filingDateBoxNumber: LPQ07):Boolean = {
    (filingDateBoxNumber.value, endDateOfWriteOffAP) match {
      case (Some(filingDate), Some(endDate)) => {
        val reliefDueDate = endDate.plusMonths(9)
        filingDate.isAfter(reliefDueDate)
      }
      case _ =>  false
    }
  }

  private def isFilingBeforeReliefDueDate(filingDate: LPQ07) =  filingDate.value match {
    case Some(date) => !isFilingAfterLaterReliefDueDate(filingDate)
    case None => true
  }

  private def requireEndDateOfWriteOffApp(acctPeriodEnd: LocalDate) = {
    val message = s"As the write off date [$dateWrittenOff] is more than 9 months after the accounting period end date [$acctPeriodEnd], the end date of the write off accounting period must be provided"
    val requirement:Boolean = if(dateWrittenOff.isAfter(acctPeriodEnd.plusMonths(9)) && endDateOfWriteOffAP.isEmpty) false else true
    require(requirement, message)
  }

}

