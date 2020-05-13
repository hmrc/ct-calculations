

package uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors

import uk.gov.hmrc.ct.accounts.frs102.calculations.LoansToDirectorsCalculator
import uk.gov.hmrc.ct.box._

case class AC309A(value: Option[Int]) extends CtBoxIdentifier(name = "Loan Balance at end of POA")
  with CtOptionalInteger {
}

object AC309A extends CompoundCalculated[AC309A, LoanToDirector] with LoansToDirectorsCalculator {

  override def calculate(loan: LoanToDirector): AC309A = {
    calculateLoanBalanceAtEndOfPOA(
      loan.ac306A,
      loan.ac307A,
      loan.ac308A
    )
  }
}
