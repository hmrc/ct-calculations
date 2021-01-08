/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC132(value: Option[Int]) extends CtBoxIdentifier(name = "Net book value of tangible assets at the end of the current period")
  with CtOptionalInteger with BalanceSheetTangibleAssetsCalculator
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators{

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    failIf (anyHaveValue(ac44(), ac45()))(
      collectErrors(
        validateMoney(value),
        validateNetBookValueMatchesTotalAssets(boxRetriever)
      )
    )
  }

  def validateNetBookValueMatchesTotalAssets(boxRetriever: Frs102AccountsBoxRetriever)() = {
    failIf(this.orZero != boxRetriever.ac44().orZero) {
      Set(CtValidation(None, "error.tangible.assets.note.currentNetBookValue.notEqualToAssets"))
    }
  }
}

object AC132 extends Calculated[AC132, Frs102AccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC132 = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(x.ac217(), x.ac131())
      case x: FullAccountsBoxRetriever => {
        import x._
        calculateAC132(
          ac132A(),
          ac132B(),
          ac132C(),
          ac132D(),
          ac132E()
        )
      }
    }
  }

}
