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
import uk.gov.hmrc.ct.computations.CP2
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600a.v3.formats.LoansFormatter
import uk.gov.hmrc.ct.domain.ValidationConstants._


case class LoansToParticipators(loans: List[Loan] = List.empty) extends CtBoxIdentifier(name = "Loans to participators.") with CtValue[List[Loan]] with Input with ValidatableBox[CT600BoxRetriever] {

  def +(other: LoansToParticipators): LoansToParticipators = new LoansToParticipators(loans ++ other.loans)

  override def value = loans

  override def asBoxString = LoansFormatter.asBoxString(this)

  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] =
    loans.foldRight(Set[CtValidation]())((loan, tail) => loan.validate(boxRetriever) ++ tail)
}

case class Loan ( id: String,
                  name: String,
                  amount: Int,
                  isRepaidWithin9Months: Option[Boolean] = None,
                  repaymentWithin9Months: Option[Repayment] = None,
                  isRepaidAfter9Months: Option[Boolean] = None,
                  otherRepayments: List[Repayment] = List.empty,
                  hasWriteOffs: Option[Boolean] = None,
                  writeOffs: List[WriteOff] = List.empty) {

  def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    validateLoan(invalidLoanNameLength, "error.loan.name.length") ++
    validateLoan(invalidLoanAmount, "error.loan.amount.value") ++
    validateLoan(invalidRepayedWithin9Months, "error.loan.isRepaidWithin9Months.required") ++
    validateRepaymentWithin9Months(boxRetriever) ++
    validateLoan(invalidRepayedAfter9Months, "error.loan.isRepaidAfter9Months.required") ++
    validateLoan(invalidHasWriteOffs, "error.loan.hasWriteOffs.required") ++
    writeOffs.foldRight(Set[CtValidation]())((writeOff, tail) => writeOff.validate(boxRetriever, id) ++ tail)
  }

  private def invalidLoanNameLength: Boolean = name.length < 2 || name.length > 56

  private def invalidLoanAmount: Boolean = amount < MIN_MONEY_AMOUNT_ALLOWED || amount > MAX_MONEY_AMOUNT_ALLOWED

  private def invalidRepayedWithin9Months: Boolean = isRepaidWithin9Months.isEmpty

  private def validateRepaymentWithin9Months(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    val dateRange = Some(Seq(boxRetriever.retrieveCP2().value.toString("dd MMMM YYYY"), boxRetriever.retrieveCP2().value.plusMonths(9).toString("dd MMMM YYYY")))
    val apEndDate = boxRetriever.retrieveCP2().value
    val apEndDatePlus9Months = boxRetriever.retrieveCP2().value.plusMonths(9)

    (isRepaidWithin9Months, repaymentWithin9Months) match {
      case (Some(true), Some(Repayment(repaymentId, _ , date, _))) if !date.isAfter(apEndDate) || date.isAfter(apEndDatePlus9Months)
        => Set(CtValidation(Some(s"LoansToParticipators"), s"loan.$id.repaymentWithin9Months.$repaymentId.error.repaymentWithin9Months.date.range", dateRange))
      case _ => Set()
    }
  }

  private def invalidRepayedAfter9Months: Boolean = isRepaidAfter9Months.isEmpty

  private def invalidHasWriteOffs: Boolean = hasWriteOffs.isEmpty

  def validateLoan(invalid: Boolean, errorMsg: String): Set[CtValidation] = {
   invalid match {
     case true => Set(CtValidation(Some(s"LoansToParticipators"), s"loan.$id.$errorMsg", None))
     case false => Set.empty
    }
  }

}

case class Repayment(id: String, amount: Int, date: LocalDate, endDateOfAP: Option[LocalDate] = None) extends LoansDateRules

case class WriteOff(id: String, amount: Int, date: LocalDate, endDateOfAP : Option[LocalDate] = None) extends LoansDateRules {

  def validate(boxRetriever: CT600BoxRetriever, loanId: String): Set[CtValidation] = {
    validateWriteOff(invalidDate(boxRetriever.retrieveCP2()), "error.writeOff.date.range", loanId) ++
    validateWriteOff(invalidWriteOffAmount, "error.writeOff.amount.value", loanId) ++
    validateWriteOff(invalidApEndDate(boxRetriever.retrieveCP2()), "error.writeOff.apEndDate.range", loanId)
  }

  private def invalidDate(cp2: CP2): Boolean = !(date.isAfter(cp2.value) && date.isBefore(LocalDate.now().plusDays(1).toDateTimeAtStartOfDay.toLocalDate))

  private def invalidWriteOffAmount: Boolean = amount < MIN_MONEY_AMOUNT_ALLOWED || amount > MAX_MONEY_AMOUNT_ALLOWED

  private def invalidApEndDate(cp2: CP2): Boolean = !endDateOfAP.map(_.isAfter(cp2.value)).getOrElse(true)

  def validateWriteOff(invalid: Boolean, errorMsg: String, loanId: String): Set[CtValidation] = {
    invalid match {
      case true => Set(CtValidation(Some(s"LoansToParticipators"), s"loan.$loanId.writeOff.$id.$errorMsg", None))
      case false => Set.empty
    }
  }
}


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
