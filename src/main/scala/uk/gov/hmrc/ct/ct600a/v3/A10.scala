package uk.gov.hmrc.ct.ct600a.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtValue}
import uk.gov.hmrc.ct.ct600a.v3.formats.Loans

/**
 * @param endDateOfRepaymentAP :  The end date of the accounting period in which the loan repayment was made
 */
case class Loan (id: String,
                 name: String,
                 amount: Int,
                 repaid: Boolean = false,
                 lastRepaymentDate: Option[LocalDate] = None,
                 totalAmountRepaid: Option[Int] = None,
                 endDateOfRepaymentAP: Option[LocalDate] = None) {

  def isRepaymentReliefEarlierThanDue(acctPeriodEnd: LocalDate): Boolean = {
    val nineMonthsAndADayAfter: LocalDate = acctPeriodEnd.plusMonths(9).plusDays(1)
    repaid && lastRepaymentIsAfter(acctPeriodEnd) && lastRepaymentBefore(nineMonthsAndADayAfter)
  }

  def isRepaymentLaterReliefNowDue(acctPeriodEnd: LocalDate, filingDate: LPQ07): Boolean = {
    repaid && !isRepaymentReliefEarlierThanDue(acctPeriodEnd) && isFilingAfterLaterReliefDueDate(filingDate)
  }

  def isRepaymentLaterReliefNotYetDue(acctPeriodEnd: LocalDate, filingDate: LPQ07): Boolean = {
    repaid && !isRepaymentReliefEarlierThanDue(acctPeriodEnd) && !isFilingAfterLaterReliefDueDate(filingDate)
  }

  val isFullyRepaid = {
    repaid && amount == totalAmountRepaid.getOrElse(0)
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

case class A10(loans: List[Loan] = List.empty) extends CtBoxIdentifier(name = "Loan to participators - participants and amounts.") with CtValue[List[Loan]] {

  def +(other: A10): A10 = new A10(loans ++ other.loans)

  override def value = loans

  override def asBoxString = Loans.asBoxString(this)
}


