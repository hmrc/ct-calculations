

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetCreditorsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC163(value: Option[Int]) extends CtBoxIdentifier(name = "Creditors after one year - Total (PY)")
  with CtOptionalInteger
  with ValidatableBox[FullAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    failIf (anyHaveValue(ac64(), ac65()))(
      collectErrors(
        validateMoney(value),
        totalEqualToCurrentAmount(boxRetriever)
      )
    )
  }

  def totalEqualToCurrentAmount(boxRetriever: FullAccountsBoxRetriever)() = {
    failIf(this.value != boxRetriever.ac65().value) {
      Set(CtValidation(None, "error.creditorsAfterOneYear.previousYearTotal.notEqualsTo.previousYearAmount"))
    }
  }

}

object AC163 extends Calculated[AC163, FullAccountsBoxRetriever] with BalanceSheetCreditorsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC163 = {
    import boxRetriever._
    calculateAC163(
      ac157(),
      ac159(),
      ac161()
    )
  }

}
