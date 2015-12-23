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
    validateLoan(invalidRepayedAfter9Months, "error.loan.isRepaidAfter9Months.required") ++
    validateLoan(invalidHasWriteOffs, "error.loan.hasWriteOffs.required") ++
    repaymentWithin9Months.map(_.validateWithin9Months(boxRetriever, id)).getOrElse(Set()) ++
    otherRepayments.foldRight(Set[CtValidation]())((repayment, tail) => repayment.validateAfter9Months(boxRetriever, id) ++ tail) ++
    writeOffs.foldRight(Set[CtValidation]())((writeOff, tail) => writeOff.validate(boxRetriever, id) ++ tail)
  }

  private def invalidLoanNameLength: Boolean = name.length < 2 || name.length > 56

  private def invalidLoanAmount: Boolean = amount < MIN_MONEY_AMOUNT_ALLOWED || amount > MAX_MONEY_AMOUNT_ALLOWED

  private def invalidRepayedWithin9Months: Boolean = isRepaidWithin9Months.isEmpty

  private def invalidRepayedAfter9Months: Boolean = isRepaidAfter9Months.isEmpty

  private def invalidHasWriteOffs: Boolean = hasWriteOffs.isEmpty

  def validateLoan(invalid: Boolean, errorMsg: String): Set[CtValidation] = {
   invalid match {
     case true => Set(CtValidation(Some(s"LoansToParticipators"), s"loan.$id.$errorMsg", None))
     case false => Set.empty
    }
  }

}

case class Repayment(id: String, amount: Int, date: LocalDate, endDateOfAP: Option[LocalDate] = None) extends LoansDateRules {

  def validateAfter9Months(boxRetriever: CT600BoxRetriever, loanId: String): Set[CtValidation] = {
    val dateErrorArgs = Some(Seq(boxRetriever.retrieveCP2().value.plusMonths(9).toString("dd MMMM YYYY"), LocalDate.now().toString("dd MMMM YYYY")))
    val apEndDateErrorArgs = Some(Seq(boxRetriever.retrieveCP2().value.toString("dd MMMM YYYY")))

    validateRepayment(invalidDateAfter9Months(boxRetriever.retrieveCP2()), "otherRepayment", "error.otherRepayment.date.range", dateErrorArgs, loanId) ++
    validateRepayment(invalidRepaymentAmount, "otherRepayment", "error.otherRepayment.amount.value", None, loanId) ++
    validateRepayment(invalidApEndDate(boxRetriever.retrieveCP2()), "otherRepayment", "error.otherRepayment.apEndDate.range", apEndDateErrorArgs, loanId)
  }

  def validateWithin9Months(boxRetriever: CT600BoxRetriever, loanId: String): Set[CtValidation] = {

    val errorArgs = Some(Seq(boxRetriever.retrieveCP2().value.toString("dd MMMM YYYY"), boxRetriever.retrieveCP2().value.plusMonths(9).toString("dd MMMM YYYY")))
    validateRepayment(invalidDateWithin9Months(boxRetriever.retrieveCP2()), "repaymentWithin9Months", "error.repaymentWithin9Months.date.range", errorArgs, loanId) ++
    validateRepayment(invalidRepaymentAmount, "repaymentWithin9Months", "error.repaymentWithin9Months.amount.value", None, loanId)
  }

  private def invalidDateWithin9Months(cp2: CP2): Boolean = !date.isAfter(cp2.value) || date.isAfter(cp2.value.plusMonths(9))

  private def invalidDateAfter9Months(cp2: CP2): Boolean = !(date.isAfter(cp2.value.plusMonths(9)) && date.isBefore(LocalDate.now().plusDays(1).toDateTimeAtStartOfDay.toLocalDate))

  private def invalidApEndDate(cp2: CP2): Boolean = !endDateOfAP.map(_.isAfter(cp2.value)).getOrElse(true)

  private def invalidRepaymentAmount: Boolean = amount < MIN_MONEY_AMOUNT_ALLOWED || amount > MAX_MONEY_AMOUNT_ALLOWED

  def validateRepayment(invalid: Boolean, errorPrefix: String, errorMsg: String, errorArgs: Option[Seq[String]], loanId: String): Set[CtValidation] = {
    invalid match {
      case true => Set(CtValidation(Some(s"LoansToParticipators"), s"loan.$loanId.$errorPrefix.$id.$errorMsg", errorArgs))
      case false => Set.empty
    }
  }
}

case class WriteOff(id: String, amount: Int, date: LocalDate, endDateOfAP : Option[LocalDate] = None) extends LoansDateRules {

  def validate(boxRetriever: CT600BoxRetriever, loanId: String): Set[CtValidation] = {
    val dateErrorArgs = Some(Seq(boxRetriever.retrieveCP2().value.plusDays(1).toString("dd MMMM YYYY"), LocalDate.now().toString("dd MMMM YYYY")))
    val apEndDateErrorArgs = Some(Seq(boxRetriever.retrieveCP2().value.toString("dd MMMM YYYY")))

    validateWriteOff(invalidDate(boxRetriever.retrieveCP2()), "error.writeOff.date.range", dateErrorArgs, loanId) ++
    validateWriteOff(invalidWriteOffAmount, "error.writeOff.amount.value", None, loanId) ++
    validateWriteOff(invalidApEndDate(boxRetriever.retrieveCP2()), "error.writeOff.apEndDate.range", apEndDateErrorArgs, loanId)
  }

  private def invalidDate(cp2: CP2): Boolean = !(date.isAfter(cp2.value) && date.isBefore(LocalDate.now().plusDays(1).toDateTimeAtStartOfDay.toLocalDate))

  private def invalidWriteOffAmount: Boolean = amount < MIN_MONEY_AMOUNT_ALLOWED || amount > MAX_MONEY_AMOUNT_ALLOWED

  private def invalidApEndDate(cp2: CP2): Boolean = !endDateOfAP.map(_.isAfter(cp2.value)).getOrElse(true)

  def validateWriteOff(invalid: Boolean, errorMsg: String, errorArgs: Option[Seq[String]], loanId: String): Set[CtValidation] = {
    invalid match {
      case true => Set(CtValidation(Some(s"LoansToParticipators"), s"loan.$loanId.writeOff.$id.$errorMsg", errorArgs))
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
