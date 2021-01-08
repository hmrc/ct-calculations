/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC123(value: Option[Int]) extends CtBoxIdentifier(name = "Net book value at [POA START]")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    failIf (anyHaveValue(ac42(), ac43()))(
      collectErrors(
        validateMoney(value, min = 0),
        validateNetBookValueMatchesTotalAssets(boxRetriever)
      )
    )
  }

  def validateNetBookValueMatchesTotalAssets(boxRetriever: Frs102AccountsBoxRetriever)() = {
    failIf(this.orZero != boxRetriever.ac43().orZero) {
      Set(CtValidation(None, "error.intangible.assets.note.previousNetBookValue.notEqualToAssets"))
    }
  }

}

object AC123 extends Calculated[AC123, Frs102AccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC123 = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => calculateAbridgedAC123(x.ac114(), x.ac118())
      case x: FullAccountsBoxRetriever => calculateFullAC123(x.ac123A(), x.ac123B())
    }
  }

}
