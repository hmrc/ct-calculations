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

package uk.gov.hmrc.ct.ct600a.v3

import org.joda.time.{DateTime, LocalDate}
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.formats.LoansFormatter
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._
import uk.gov.hmrc.ct.utils.DateImplicits._

case class LoansToParticipators(loans: List[Loan] = List.empty) extends CtBoxIdentifier(name = "Loans to participators.") with CtValue[List[Loan]] with Input with ValidatableBox[CT600ABoxRetriever] {

  def +(other: LoansToParticipators): LoansToParticipators = LoansToParticipators(loans ++ other.loans)

  override def value = loans

  override def asBoxString = LoansFormatter.asBoxString(this)

  override def validate(boxRetriever: CT600ABoxRetriever): Set[CtValidation] = {
    validateLoanRequired(boxRetriever) ++
    loans.foldRight(Set[CtValidation]())((loan, tail) => loan.validate(boxRetriever, this) ++ tail)
  }

  def validateLoanRequired(boxRetriever: CT600ABoxRetriever): Set[CtValidation] = {
    boxRetriever.lpq03().value.getOrElse(false) match {
      case true if loans.isEmpty => Set(CtValidation(Some("LoansToParticipators"), "error.loan.required", None))
      case _ => Set.empty
    }
  }
}

case class Loan ( id: String,
                  name: Option[String],
                  amount: Option[Int],
                  amountBefore06042016: Option[Int] = None,
                  repaymentWithin9Months: Option[Repayment] = None,
                  otherRepayments: List[Repayment] = List.empty,
                  writeOffs: List[WriteOff] = List.empty) {

  def validate(boxRetriever: CT600ABoxRetriever, loansToParticipators: LoansToParticipators): Set[CtValidation] = {
    val loanIndex = LoansToParticipators.findLoanIndex(this, loansToParticipators)

    validateLoan(invalidLoanNameLength, s"loans.$loanIndex.name.length") ++
    validateLoan(invalidLoanNameUnique(loansToParticipators), s"loans.$loanIndex.uniqueName") ++
    validateLoan(invalidLoanAmount, s"loans.$loanIndex.amount.value") ++
    validateLoan(invalidBalancedAmount, s"loans.$loanIndex.unbalanced", balancedAmountArgs) ++
    validateLoan(invalidLoanBeforeApril2016Amount, s"loans.$loanIndex.beforeApril2016Amount.value", Some(Seq(amount.getOrElse(0).toString))) ++
    validateLoan(invalidBalancedBeforeApril2016Amount, s"loans.$loanIndex.unbalanced.beforeApril2016Amount", balancedBeforeApril2016AmountArgs) ++
    repaymentWithin9Months.map(_.validateWithin9Months(boxRetriever, loanIndex)).getOrElse(Set()) ++
    otherRepayments.foldRight(Set[CtValidation]())((repayment, tail) => repayment.validateAfter9Months(boxRetriever, loansToParticipators, loanIndex) ++ tail) ++
    writeOffs.foldRight(Set[CtValidation]())((writeOff, tail) => writeOff.validate(boxRetriever, loansToParticipators, loanIndex) ++ tail)
  }

  private def invalidLoanNameLength: Boolean = name.exists(_.length < 2) || name.exists(_.length > 56) || name.isEmpty

  private def invalidLoanNameUnique(loansToParticipators: LoansToParticipators): Boolean = {
    loansToParticipators.loans.exists(loan => loan.id != id && loan.name.exists(existingName => name.exists(_.trim.toLowerCase == existingName.trim.toLowerCase)))
  }

  private def invalidLoanAmount: Boolean = amount.exists(_ < MIN_MONEY_AMOUNT_ALLOWED) || amount.exists(_ > MAX_MONEY_AMOUNT_ALLOWED) || amount.isEmpty

  private def invalidBalancedAmount: Boolean = amount.exists(_ < totalAmountRepaymentsAndWriteOffs)

  private def invalidBalancedBeforeApril2016Amount: Boolean = {
    amountBefore06042016 match {
      case Some(am) => totalAmountBeforeApril2016RepaymentsAndWriteOffs > am
      case None => totalAmountBeforeApril2016RepaymentsAndWriteOffs > 0
    }
  }

  private def invalidLoanBeforeApril2016Amount: Boolean = amountBefore06042016.exists(ab => ab < 0 || amount.exists(_ < ab))

  def totalAmountRepaymentsAndWriteOffs: Int = {
    val within9MonthsAmount: Int = repaymentWithin9Months.flatMap(_.amount).getOrElse(0)
    val otherRepaymentsAmount: Int = otherRepayments.map(_.amount.getOrElse(0)).sum
    val writeOffsAmount: Int = writeOffs.map(_.amount.getOrElse(0)).sum

    within9MonthsAmount + otherRepaymentsAmount + writeOffsAmount
  }

  def totalAmountBeforeApril2016RepaymentsAndWriteOffs: Int = {

    val repaymentBefore2016Total: Int = repaymentWithin9Months.flatMap(_.amountBefore06042016).sum
    val otherRepaymentBefore2016Total: Int = otherRepayments.flatMap(_.amountBefore06042016).sum
    val writeOffBefore2016Total: Int = writeOffs.flatMap(_.amountBefore06042016).sum

    repaymentBefore2016Total + otherRepaymentBefore2016Total + writeOffBefore2016Total
  }

  private def balancedAmountArgs: Option[Seq[String]] = Some(Seq(totalAmountRepaymentsAndWriteOffs.toString, amount.getOrElse(0).toString))

  private def balancedBeforeApril2016AmountArgs: Option[Seq[String]] = Some(Seq(totalAmountBeforeApril2016RepaymentsAndWriteOffs.toString, amountBefore06042016.getOrElse(0).toString))

  def validateLoan(invalid: Boolean, errorMsg: String, errorArgs: Option[Seq[String]] = None): Set[CtValidation] = {
   invalid match {
     case true => Set(CtValidation(Some(s"LoansToParticipators"), s"error.compoundList.$errorMsg", errorArgs))
     case false => Set.empty
    }
  }

}

case class Repayment(id: String, amount: Option[Int], amountBefore06042016: Option[Int] = None, date: Option[LocalDate], endDateOfAP: Option[LocalDate] = None) extends LoansDateRules {

  val repaymentWithin9monthsErrorCode = "repaymentWithin9Months"
  val repaymentAfter9MonthsErrorCode = "otherRepayments"

  def validateAfter9Months(boxRetriever: CT600ABoxRetriever, loansToParticipators: LoansToParticipators, loanIndex: Int): Set[CtValidation] = {
    val repaymentIndex = LoansToParticipators.findOtherRepaymentIndex(loanIndex, this, loansToParticipators)

    validateRepayment(invalidDateAfter9Months(boxRetriever), s"$repaymentAfter9MonthsErrorCode.$repaymentIndex.date.range", errorArgsOtherRepaymentsDate(boxRetriever), loanIndex) ++
    validateRepayment(invalidRepaymentAmount, s"$repaymentAfter9MonthsErrorCode.$repaymentIndex.amount.value", None, loanIndex) ++
    validateRepayment(invalidRepaymentBeforeApril2016AmountAfter9Months, s"$repaymentAfter9MonthsErrorCode.$repaymentIndex.beforeApril2016Amount.value", Some(Seq(amount.getOrElse(0).toString)), loanIndex) ++
    validateRepayment(invalidApEndDateRequired, s"$repaymentAfter9MonthsErrorCode.$repaymentIndex.endDateOfAP.required", None, loanIndex) ++
    validateRepayment(invalidApEndDateRange(boxRetriever), s"$repaymentAfter9MonthsErrorCode.$repaymentIndex.endDateOfAP.range", errorArgsOtherRepaymentsApEndDate(boxRetriever), loanIndex)
  }

  def validateWithin9Months(boxRetriever: CT600ABoxRetriever, loanIndex: Int): Set[CtValidation] = {
    validateRepayment(invalidDateWithin9Months(boxRetriever), s"$repaymentWithin9monthsErrorCode.date.range",  errorArgsRepaymentsWith9MonthsDate(boxRetriever), loanIndex) ++
    validateRepayment(invalidRepaymentAmount, s"$repaymentWithin9monthsErrorCode.amount.value", None, loanIndex) ++
    validateRepayment(invalidRepaymentBeforeApril2016AmountWithin9Months, s"$repaymentWithin9monthsErrorCode.beforeApril2016Amount.value", Some(Seq(amount.getOrElse(0).toString)), loanIndex)
  }

  private def invalidDateWithin9Months(boxRetriever: CT600ABoxRetriever): Boolean = !date.exists(_ > currentAPEndDate(boxRetriever)) || date.exists(_ > earlierOfNowAndAPEndDatePlus9Months(boxRetriever)) || date.isEmpty

  private def invalidDateAfter9Months(boxRetriever: CT600ABoxRetriever): Boolean = {
    !(date.exists(_ > currentAPEndDatePlus9Months(boxRetriever)) && date.exists(_ <= DateHelper.now().toDateTimeAtStartOfDay.toLocalDate)) || date.isEmpty
  }

  private def invalidApEndDateRequired: Boolean = endDateOfAP.isEmpty

  private def invalidApEndDateRange(boxRetriever: CT600ABoxRetriever): Boolean = !endDateOfAP.map(_ > currentAPEndDate(boxRetriever)).getOrElse(true)

  private def invalidRepaymentAmount: Boolean = amount.exists(_ < MIN_MONEY_AMOUNT_ALLOWED) || amount.exists(_ > MAX_MONEY_AMOUNT_ALLOWED) || amount.isEmpty

  private def invalidRepaymentBeforeApril2016AmountWithin9Months: Boolean = amountBefore06042016.exists(ab => ab < 0 || amount.exists(_ < ab))

  private def invalidRepaymentBeforeApril2016AmountAfter9Months: Boolean = amountBefore06042016.exists(ab => ab < 0 || amount.exists(_ < ab))

  def validateRepayment(invalid: Boolean, errorMsg: String, errorArgs: Option[Seq[String]], loanIndex: Int): Set[CtValidation] = {
    invalid match {
      case true => Set(CtValidation(Some(s"LoansToParticipators"), s"error.compoundList.loans.$loanIndex.$errorMsg", errorArgs))
      case false => Set.empty
    }
  }

  def currentAPEndDatePlus9Months(boxRetriever: CT600ABoxRetriever): LocalDate = boxRetriever.cp2().value.plusMonths(9)

  def currentAPEndDate(boxRetriever: CT600ABoxRetriever): LocalDate = boxRetriever.cp2().value

  def earlierOfNowAndAPEndDatePlus9Months(boxRetriever: CT600ABoxRetriever): LocalDate = {
    currentAPEndDatePlus9Months(boxRetriever) < dateAtStartofToday match {
      case true => currentAPEndDatePlus9Months(boxRetriever)
      case _ => dateAtStartofToday
    }
  }

  def dateAtStartofToday: LocalDate = DateHelper.now().toDateTimeAtStartOfDay.toLocalDate

  def errorArgsRepaymentsWith9MonthsDate(boxRetriever: CT600ABoxRetriever): Some[Seq[String]] =
    Some(Seq(toErrorArgsFormat(currentAPEndDate(boxRetriever)), toErrorArgsFormat(earlierOfNowAndAPEndDatePlus9Months(boxRetriever))))

  def errorArgsOtherRepaymentsDate(boxRetriever: CT600ABoxRetriever): Some[Seq[String]] =
    Some(Seq(toErrorArgsFormat(currentAPEndDatePlus9Months(boxRetriever).plusDays(1)), toErrorArgsFormat(DateHelper.now())))

  def errorArgsOtherRepaymentsApEndDate(boxRetriever: CT600ABoxRetriever): Some[Seq[String]] =
    Some(Seq(toErrorArgsFormat(currentAPEndDate(boxRetriever))))
}

case class WriteOff(id: String, amount: Option[Int], amountBefore06042016: Option[Int] = None, date: Option[LocalDate], endDateOfAP : Option[LocalDate] = None) extends LoansDateRules {

  private val writeOffErrorCode = "writeOffs"

  def validate(boxRetriever: CT600ABoxRetriever, loansToParticipators: LoansToParticipators, loanIndex: Int): Set[CtValidation] = {
    val writeOffIndex = LoansToParticipators.findWriteOffIndex(loanIndex, this, loansToParticipators)

    validateWriteOff(invalidDate(boxRetriever), s"$writeOffErrorCode.$writeOffIndex.date.range", errorArgsWriteOffDate(boxRetriever), loanIndex) ++
    validateWriteOff(invalidWriteOffAmount, s"$writeOffErrorCode.$writeOffIndex.amount.value", None, loanIndex) ++
    validateWriteOff(invalidWriteOffBeforeApril2016Amount, s"$writeOffErrorCode.$writeOffIndex.beforeApril2016Amount.value", Some(Seq(amount.toString)), loanIndex) ++
    validateWriteOff(invalidApEndDateRequired(boxRetriever), s"$writeOffErrorCode.$writeOffIndex.endDateOfAP.required", None, loanIndex) ++
    validateWriteOff(invalidApEndDateRange(boxRetriever), s"$writeOffErrorCode.$writeOffIndex.endDateOfAP.range", errorArgsWriteOffApEndDate(boxRetriever), loanIndex)
  }

  private def invalidDate(boxRetriever: CT600ABoxRetriever): Boolean = !(date.exists(_ > currentAPEndDate(boxRetriever)) && date.exists(_ < DateHelper.now().plusDays(1).toDateTimeAtStartOfDay.toLocalDate)) || date.isEmpty

  private def invalidWriteOffAmount: Boolean = amount.exists(_ < MIN_MONEY_AMOUNT_ALLOWED) || amount.exists(_ > MAX_MONEY_AMOUNT_ALLOWED) || amount.isEmpty

  private def invalidWriteOffBeforeApril2016Amount: Boolean = amountBefore06042016.exists(ab => ab < 0 || amount.exists(_ < ab))

  private def invalidApEndDateRequired(boxRetriever: CT600ABoxRetriever): Boolean = {
    date.exists(_ > currentAPEndDatePlus9Months(boxRetriever)) match {
      case true => endDateOfAP.isEmpty
      case _ => false
    }
  }

  private def invalidApEndDateRange(boxRetriever: CT600ABoxRetriever): Boolean = !endDateOfAP.map(_ > currentAPEndDate(boxRetriever)).getOrElse(true)

  def validateWriteOff(invalid: Boolean, errorMsg: String, errorArgs: Option[Seq[String]], loanIndex: Int): Set[CtValidation] = {
    invalid match {
      case true => Set(CtValidation(Some(s"LoansToParticipators"), s"error.compoundList.loans.$loanIndex.$errorMsg", errorArgs))
      case false => Set.empty
    }
  }

  def currentAPEndDate(boxRetriever: CT600ABoxRetriever): LocalDate = boxRetriever.cp2().value

  def currentAPEndDatePlus9Months(boxRetriever: CT600ABoxRetriever): LocalDate = boxRetriever.cp2().value.plusMonths(9)

  def errorArgsWriteOffApEndDate(boxRetriever: CT600ABoxRetriever): Some[Seq[String]] =
    Some(Seq(toErrorArgsFormat(boxRetriever.cp2().value)))

  def errorArgsWriteOffDate(boxRetriever: CT600ABoxRetriever): Some[Seq[String]] =
    Some(Seq(toErrorArgsFormat(boxRetriever.cp2().value.plusDays(1)), toErrorArgsFormat(DateHelper.now())))

}

object LoansToParticipators {

  val MIN_DATE = new LocalDate(0)

  def sortLoans(loans: List[Loan]): List[Loan] = loans.sortWith(_.id < _.id).sortWith(_.name.getOrElse("").toLowerCase() < _.name.getOrElse("").toLowerCase())

  def findLoanIndex(loan: Loan, loansToParticipators: LoansToParticipators): Int = {
    sortLoans(loansToParticipators.loans).indexOf(loan)
  }

  def sortOtherRepayments(otherRepayments: List[Repayment]): List[Repayment] = {
    otherRepayments.sortWith(_.id < _.id).sortWith((r1, r2) => {
      r1.date.getOrElse(MIN_DATE).isBefore(r2.date.getOrElse(MIN_DATE))
    })
  }

  def findOtherRepaymentIndex(loanIndex: Int, repayment: Repayment, loansToParticipators: LoansToParticipators): Int = {
    sortOtherRepayments(loansToParticipators.loans(loanIndex).otherRepayments).indexOf(repayment)
  }

  def sortWriteOffs(writeOffs: List[WriteOff]): List[WriteOff] = {
    writeOffs.sortWith(_.id < _.id).sortWith((wo1, wo2) => {
      wo1.date.getOrElse(MIN_DATE).isBefore(wo2.date.getOrElse(MIN_DATE))
    })
  }

  def findWriteOffIndex(loanIndex: Int, writeOff: WriteOff, loansToParticipators: LoansToParticipators): Int = {
    sortWriteOffs(loansToParticipators.loans(loanIndex).writeOffs).indexOf(writeOff)
  }

}

trait LoansDateRules {

  val date: Option[LocalDate]
  val endDateOfAP: Option[LocalDate]

  def isReliefEarlierThanDue(acctPeriodEnd: LocalDate): Boolean = {
    val nineMonthsAndADayAfter: LocalDate = acctPeriodEnd.plusMonths(9).plusDays(1)
    date.exists(_ > acctPeriodEnd) && date.exists(_ < nineMonthsAndADayAfter)
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
        filingDate > reliefDueDate
      }
      case _ => false
    }
  }

  private def isFilingBeforeReliefDueDate(filingDate: LPQ07) = filingDate.value match {
    case Some(_) => !isFilingAfterLaterReliefDueDate(filingDate)
    case None => true
  }

}
