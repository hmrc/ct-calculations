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

package uk.gov.hmrc.ct.ct600a.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.formats.LoansFormatter
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._
import uk.gov.hmrc.ct.utils.DateImplicits._

case class LoansToParticipators(loans: List[Loan] = List.empty) extends CtBoxIdentifier(name = "Loans to participators.") with CtValue[List[Loan]] with Input with ValidatableBox[CT600ABoxRetriever] {

  def +(other: LoansToParticipators): LoansToParticipators = new LoansToParticipators(loans ++ other.loans)

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
                  name: String,
                  amount: Int,
                  amountBefore06042016: Option[Int] = None,
                  repaymentWithin9Months: Option[Repayment] = None,
                  otherRepayments: List[Repayment] = List.empty,
                  writeOffs: List[WriteOff] = List.empty) {

  def validate(boxRetriever: CT600ABoxRetriever, loansToParticipators: LoansToParticipators): Set[CtValidation] = {
    validateLoan(invalidLoanNameLength, "error.loan.name.length") ++
    validateLoan(invalidLoanNameUnique(loansToParticipators), "error.loan.uniqueName") ++
    validateLoan(invalidLoanAmount, "error.loan.amount.value") ++
    validateLoan(invalidBalancedAmount, "error.loan.unbalanced", balancedAmountArgs) ++
    validateLoan(invalidLoanBeforeApril2016Amount, "error.loan.beforeApril2016Amount.value", Some(Seq(amount.toString))) ++
    validateLoan(invalidBalancedBeforeApril2016Amount, "error.loan.unbalanced.beforeApril2016Amount", balancedBeforeApril2016AmountArgs) ++
    repaymentWithin9Months.map(_.validateWithin9Months(boxRetriever, id)).getOrElse(Set()) ++
    otherRepayments.foldRight(Set[CtValidation]())((repayment, tail) => repayment.validateAfter9Months(boxRetriever, id) ++ tail) ++
    writeOffs.foldRight(Set[CtValidation]())((writeOff, tail) => writeOff.validate(boxRetriever, id) ++ tail)
  }

  private def invalidLoanNameLength: Boolean = name.length < 2 || name.length > 56

  private def invalidLoanNameUnique(loansToParticipators: LoansToParticipators): Boolean = {
    loansToParticipators.loans.exists(loan => loan.id != id && loan.name.trim.toLowerCase == name.trim.toLowerCase)
  }

  private def invalidLoanAmount: Boolean = amount < MIN_MONEY_AMOUNT_ALLOWED || amount > MAX_MONEY_AMOUNT_ALLOWED

  private def invalidBalancedAmount: Boolean = amount < totalAmountRepaymentsAndWriteOffs

  private def invalidBalancedBeforeApril2016Amount: Boolean = {
    amountBefore06042016 match {
      case Some(am) => totalAmountBeforeApril2016RepaymentsAndWriteOffs > am
      case None => totalAmountBeforeApril2016RepaymentsAndWriteOffs > 0
    }
  }

  private def invalidLoanBeforeApril2016Amount: Boolean = amountBefore06042016.exists(ab => ab < 0 || ab > amount)

  def totalAmountRepaymentsAndWriteOffs: Int =
    repaymentWithin9Months.map(_.amount).getOrElse(0) + otherRepayments.foldRight(0)((h, t) => h.amount + t) + writeOffs.foldRight(0)((h, t) => h.amount + t)

  def totalAmountBeforeApril2016RepaymentsAndWriteOffs: Int = {

    val repaymentBefore2016Total: Int = repaymentWithin9Months.flatMap(_.amountBefore06042016).sum
    val otherRepaymentBefore2016Total: Int = otherRepayments.flatMap(_.amountBefore06042016).sum
    val writeOffBefore2016Total: Int = writeOffs.flatMap(_.amountBefore06042016).sum

    repaymentBefore2016Total + otherRepaymentBefore2016Total + writeOffBefore2016Total
  }

  private def balancedAmountArgs: Option[Seq[String]] = Some(Seq(totalAmountRepaymentsAndWriteOffs.toString, amount.toString))

  private def balancedBeforeApril2016AmountArgs: Option[Seq[String]] = Some(Seq(totalAmountBeforeApril2016RepaymentsAndWriteOffs.toString, amountBefore06042016.getOrElse(0).toString))

  def validateLoan(invalid: Boolean, errorMsg: String, errorArgs: Option[Seq[String]] = None): Set[CtValidation] = {
   invalid match {
     case true => Set(CtValidation(Some(s"LoansToParticipators"), s"loan.$id.$errorMsg", errorArgs))
     case false => Set.empty
    }
  }

}

case class Repayment(id: String, amount: Int, amountBefore06042016: Option[Int] = None, date: LocalDate, endDateOfAP: Option[LocalDate] = None) extends LoansDateRules {

  val repaymentWithin9monthsErrorCode = "repaymentWithin9Months"
  val repaymentAfter9MonthsErrorCode = "otherRepayment"

  def validateAfter9Months(boxRetriever: CT600ABoxRetriever, loanId: String): Set[CtValidation] = {
    validateRepayment(invalidDateAfter9Months(boxRetriever), repaymentAfter9MonthsErrorCode, s"error.$repaymentAfter9MonthsErrorCode.date.range", errorArgsOtherRepaymentsDate(boxRetriever), loanId) ++
    validateRepayment(invalidRepaymentAmount, repaymentAfter9MonthsErrorCode, s"error.$repaymentAfter9MonthsErrorCode.amount.value", None, loanId) ++
    validateRepayment(invalidRepaymentBeforeApril2016AmountAfter9Months, repaymentAfter9MonthsErrorCode, s"error.$repaymentAfter9MonthsErrorCode.beforeApril2016Amount.value", Some(Seq(amount.toString)), loanId) ++
    validateRepayment(invalidApEndDateRequired, repaymentAfter9MonthsErrorCode, s"error.$repaymentAfter9MonthsErrorCode.apEndDate.required", None, loanId) ++
    validateRepayment(invalidApEndDateRange(boxRetriever), repaymentAfter9MonthsErrorCode, s"error.$repaymentAfter9MonthsErrorCode.apEndDate.range", errorArgsOtherRepaymentsApEndDate(boxRetriever), loanId)
  }

  def validateWithin9Months(boxRetriever: CT600ABoxRetriever, loanId: String): Set[CtValidation] = {
    validateRepayment(invalidDateWithin9Months(boxRetriever), repaymentWithin9monthsErrorCode, s"error.$repaymentWithin9monthsErrorCode.date.range",  errorArgsRepaymentsWith9MonthsDate(boxRetriever), loanId) ++
    validateRepayment(invalidRepaymentAmount, repaymentWithin9monthsErrorCode, s"error.$repaymentWithin9monthsErrorCode.amount.value", None, loanId) ++
    validateRepayment(invalidRepaymentBeforeApril2016AmountWithin9Months, repaymentWithin9monthsErrorCode, s"error.$repaymentWithin9monthsErrorCode.beforeApril2016Amount.value", Some(Seq(amount.toString)), loanId)
  }

  private def invalidDateWithin9Months(boxRetriever: CT600ABoxRetriever): Boolean = !(date > currentAPEndDate(boxRetriever)) || date > earlierOfNowAndAPEndDatePlus9Months(boxRetriever)

  private def invalidDateAfter9Months(boxRetriever: CT600ABoxRetriever): Boolean = {
    !(date > currentAPEndDatePlus9Months(boxRetriever) && date < DateHelper.now().plusDays(1).toDateTimeAtStartOfDay.toLocalDate)
  }

  private def invalidApEndDateRequired: Boolean = endDateOfAP.isEmpty

  private def invalidApEndDateRange(boxRetriever: CT600ABoxRetriever): Boolean = !endDateOfAP.map(_ > currentAPEndDate(boxRetriever)).getOrElse(true)

  private def invalidRepaymentAmount: Boolean = amount < MIN_MONEY_AMOUNT_ALLOWED || amount > MAX_MONEY_AMOUNT_ALLOWED

  private def invalidRepaymentBeforeApril2016AmountWithin9Months: Boolean = amountBefore06042016.exists(ab => ab < 0 || ab > amount)

  private def invalidRepaymentBeforeApril2016AmountAfter9Months: Boolean = amountBefore06042016.exists(ab => ab < 0 || ab > amount)

  def validateRepayment(invalid: Boolean, errorPrefix: String, errorMsg: String, errorArgs: Option[Seq[String]], loanId: String): Set[CtValidation] = {
    invalid match {
      case true => Set(CtValidation(Some(s"LoansToParticipators"), s"loan.$loanId.$errorPrefix.$id.$errorMsg", errorArgs))
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

case class WriteOff(id: String, amount: Int, amountBefore06042016: Option[Int] = None, date: LocalDate, endDateOfAP : Option[LocalDate] = None) extends LoansDateRules {

  private val writeOffErrorCode = "writeOff"

  def validate(boxRetriever: CT600ABoxRetriever, loanId: String): Set[CtValidation] = {
    validateWriteOff(invalidDate(boxRetriever), s"error.$writeOffErrorCode.date.range", errorArgsWriteOffDate(boxRetriever), loanId) ++
    validateWriteOff(invalidWriteOffAmount, s"error.$writeOffErrorCode.amount.value", None, loanId) ++
    validateWriteOff(invalidWriteOffBeforeApril2016Amount, s"error.$writeOffErrorCode.beforeApril2016Amount.value", Some(Seq(amount.toString)), loanId) ++
    validateWriteOff(invalidApEndDateRequired(boxRetriever), s"error.$writeOffErrorCode.apEndDate.required", None, loanId) ++
    validateWriteOff(invalidApEndDateRange(boxRetriever), s"error.$writeOffErrorCode.apEndDate.range", errorArgsWriteOffApEndDate(boxRetriever), loanId)
  }

  private def invalidDate(boxRetriever: CT600ABoxRetriever): Boolean = !(date > currentAPEndDate(boxRetriever) && date < DateHelper.now().plusDays(1).toDateTimeAtStartOfDay.toLocalDate)

  private def invalidWriteOffAmount: Boolean = amount < MIN_MONEY_AMOUNT_ALLOWED || amount > MAX_MONEY_AMOUNT_ALLOWED

  private def invalidWriteOffBeforeApril2016Amount: Boolean = amountBefore06042016.exists(ab => ab < 0 || ab > amount)

  private def invalidApEndDateRequired(boxRetriever: CT600ABoxRetriever): Boolean = {
    date > currentAPEndDatePlus9Months(boxRetriever) match {
      case true => endDateOfAP.isEmpty
      case _ => false
    }
  }

  private def invalidApEndDateRange(boxRetriever: CT600ABoxRetriever): Boolean = !endDateOfAP.map(_ > currentAPEndDate(boxRetriever)).getOrElse(true)

  def validateWriteOff(invalid: Boolean, errorMsg: String, errorArgs: Option[Seq[String]], loanId: String): Set[CtValidation] = {
    invalid match {
      case true => Set(CtValidation(Some(s"LoansToParticipators"), s"loan.$loanId.$writeOffErrorCode.$id.$errorMsg", errorArgs))
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


trait LoansDateRules {

  val date: LocalDate
  val endDateOfAP: Option[LocalDate]

  def isReliefEarlierThanDue(acctPeriodEnd: LocalDate): Boolean = {
    val nineMonthsAndADayAfter: LocalDate = acctPeriodEnd.plusMonths(9).plusDays(1)
    date > acctPeriodEnd && date < nineMonthsAndADayAfter
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
