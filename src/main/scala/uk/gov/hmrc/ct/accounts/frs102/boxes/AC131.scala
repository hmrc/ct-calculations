

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC131(value: Option[Int]) extends CtBoxIdentifier(name = "Total net assets or liabilities (previous PoA)")
  with CtOptionalInteger
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value)
    )
  }
}

object AC131 extends Calculated[AC131, Frs102AccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC131 = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(x.ac128(), x.ac219(), x.ac130(), x.ac214())
      case x: FullAccountsBoxRetriever => {
        import x._
        calculateAC131(
          ac131A(),
          ac131B(),
          ac131C(),
          ac131D(),
          ac131E()
        )
      }
    }
  }

}
