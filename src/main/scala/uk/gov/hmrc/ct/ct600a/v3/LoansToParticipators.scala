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
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600a.v3.formats.LoansFormatter


case class LoansToParticipators(loans: List[Loan] = List.empty) extends CtBoxIdentifier(name = "Loans to participators.") with CtValue[List[Loan]] with Input with ValidatableBox[CT600BoxRetriever] {

  def +(other: LoansToParticipators): LoansToParticipators = new LoansToParticipators(loans ++ other.loans)

  override def value = loans

  override def asBoxString = LoansFormatter.asBoxString(this)

  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    validateLoans(invalidLoanNameLength, "error.name.length") ++
    validateLoans(invalidLoanAmount, "error.amount.value") ++
    validateLoans(invalidRepayedWithin9Months, "error.isRepaidWithin9Months.required") ++
    validateLoans(invalidRepayedAfter9Months, "error.isRepaidAfter9Months.required") ++
    validateLoans(invalidHasWriteOffs, "error.hasWriteOffs.required")
  }

  private def invalidLoanNameLength(loan: Loan): Boolean = loan.name.length < 2 || loan.name.length > 56

  private def invalidLoanAmount(loan: Loan): Boolean = loan.amount < 1 || loan.amount > 99999999

  private def invalidRepayedWithin9Months(loan: Loan): Boolean = loan.isRepaidWithin9Months.isEmpty

  private def invalidRepayedAfter9Months(loan: Loan): Boolean = loan.isRepaidAfter9Months.isEmpty

  private def invalidHasWriteOffs(loan: Loan): Boolean = loan.hasWriteOffs.isEmpty

  def validateLoans(invalid: Loan => Boolean, errorMsg: String): Set[CtValidation] = {
    loans.filter(invalid).map { loan =>
        CtValidation(Some(s"loan.${loan.id}"), s"loan.${loan.id}.$errorMsg", None)
    }.toSet
  }
}

case class Loan ( id: String,
                  name: String,
                  amount: Int,
                  isRepaidWithin9Months: Option[Boolean] = None,
                  repaymentWithin9Months: Option[Repayment] = None,
                  isRepaidAfter9Months: Option[Boolean] = None,
                  otherRepayments: List[Repayment] = List.empty,
                  hasWriteOffs: Option[Boolean] = None,
                  writeOffs: List[WriteOff] = List.empty)

case class Repayment(id: String, amount: Int, date: LocalDate, endDateOfAP: Option[LocalDate] = None) extends LoansDateRules

case class WriteOff(id: String, amount: Int, date: LocalDate, endDateOfAP : Option[LocalDate] = None) extends LoansDateRules


trait LoansDateRules {

  val date: LocalDate
  val endDateOfAP: Option[LocalDate]

  def isReliefEarlierThanDue(acctPeriodEnd: LocalDate): Boolean = {
    val nineMonthsAndADayAfter: LocalDate = acctPeriodEnd.plusMonths(9).plusDays(1)
    date.isAfter(acctPeriodEnd) && date.isBefore(nineMonthsAndADayAfter)
  }

  def isLaterReliefNowDue(acctPeriodEnd: LocalDate, filingDate: LPQ07): Boolean = {
    !isReliefEarlierThanDue(acctPeriodEnd) && isFilingAfterLaterReliefDueDate(filingDate)
  }

  def isLaterReliefNotYetDue(acctPeriodEnd: LocalDate, filingDate: LPQ07): Boolean = {
    !isReliefEarlierThanDue(acctPeriodEnd) && isFilingBeforeReliefDueDate(filingDate)
  }

  private def isFilingAfterLaterReliefDueDate(filingDateBoxNumber: LPQ07): Boolean = {
    (filingDateBoxNumber.value, endDateOfAP) match {
      case (Some(filingDate), Some(endDate)) => {
        val reliefDueDate = endDate.plusMonths(9)
        filingDate.isAfter(reliefDueDate)
      }
      case _ => false
    }
  }

  private def isFilingBeforeReliefDueDate(filingDate: LPQ07) = filingDate.value match {
    case Some(date) => !isFilingAfterLaterReliefDueDate(filingDate)
    case None => true
  }

}
