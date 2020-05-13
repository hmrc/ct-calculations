

package uk.gov.hmrc.ct.accounts.frs102.calculations
import uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors.{AC306A, AC307A, AC308A, AC309A}
import uk.gov.hmrc.ct.box.CtTypeConverters

trait LoansToDirectorsCalculator extends CtTypeConverters {

  def calculateLoanBalanceAtEndOfPOA(ac306A: AC306A, ac307A: AC307A, ac308A: AC308A): AC309A = {
    (ac306A.value, ac307A.value, ac308A.value) match {
      case (None, None, None) => AC309A(None)
      case _ => AC309A(Some(ac306A.orZero + ac307A.orZero - ac308A.orZero))
    }
  }
}
