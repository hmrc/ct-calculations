/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC121B(value: Option[Int]) extends CtBoxIdentifier(name = "Intangible assets - Other - Amortisation - Amortisation at [POA END]")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}

object AC121B extends Calculated[AC121B, FullAccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC121B = {
    import boxRetriever._
    calculateAC121B(ac118B(), ac119B(), ac120B(), ac211B())
  }

}
