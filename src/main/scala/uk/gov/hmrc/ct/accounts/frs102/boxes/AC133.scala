/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC133(value: Option[Int]) extends CtBoxIdentifier(name = "Net book value of tangible assets at the end of the previous period")
  with CtOptionalInteger
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators{

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    failIf (anyHaveValue(ac44(), ac45()))(
      collectErrors(
        validateNetBookValueMatchesTotalAssets(boxRetriever)
      )
    )
  }

  def validateNetBookValueMatchesTotalAssets(boxRetriever: Frs102AccountsBoxRetriever)() = {
    failIf(this.orZero != boxRetriever.ac45().orZero) {
      Set(CtValidation(None, "error.tangible.assets.note.previousNetBookValue.notEqualToAssets"))
    }
  }
}

object AC133 extends Calculated[AC133, Frs102AccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC133 = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(x.ac124(), x.ac128())
      case x: FullAccountsBoxRetriever => {
        import x._
        calculateAC133(
          ac133A(),
          ac133B(),
          ac133C(),
          ac133D(),
          ac133E()
        )
      }
    }
  }
}
