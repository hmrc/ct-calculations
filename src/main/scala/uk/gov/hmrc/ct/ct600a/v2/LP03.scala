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

package uk.gov.hmrc.ct.ct600a.v2

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtValue, Input}
import uk.gov.hmrc.ct.ct600a.v2.formats.Loans
import uk.gov.hmrc.ct.utils.DateImplicits._

case class WriteOff(loanId: String, amountWrittenOff: Int, dateWrittenOff: LocalDate, endDateOfWriteOffAP : Option[LocalDate] = None) {

  def isReliefEarlierThanDue(acctPeriodEnd: LocalDate): Boolean = {
    requireEndDateOfWriteOffApp(acctPeriodEnd)
    val nineMonthsAndADayAfter: LocalDate = acctPeriodEnd.plusMonths(9).plusDays(1)
    dateWrittenOff > acctPeriodEnd && dateWrittenOff < nineMonthsAndADayAfter
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
        filingDate > reliefDueDate
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
    val requirement:Boolean = if(dateWrittenOff > acctPeriodEnd.plusMonths(9) && endDateOfWriteOffAP.isEmpty) false else true
    require(requirement, message)
  }

}

case class LP03(writeOffs: Option[List[WriteOff]] = None) extends CtBoxIdentifier(name = "Loan write offs and releases.") with CtValue[Option[List[WriteOff]]] with Input {

  override def value = writeOffs

  def +(other: LP03): LP03 = new LP03(Some(writeOffs.getOrElse(Nil) ++ other.writeOffs.getOrElse(Nil)))

  override def asBoxString = Loans.asBoxString(this)
}
