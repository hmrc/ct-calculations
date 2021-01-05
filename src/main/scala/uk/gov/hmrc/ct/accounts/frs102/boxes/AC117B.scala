/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC117B(value: Option[Int]) extends CtBoxIdentifier(name = "Intangible assets - Other - Cost - Cost at [POA END]")
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


object AC117B extends Calculated[AC117B, FullAccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC117B = {
    import boxRetriever._
    calculateAC117B(ac114B(), ac115B(), ac116B(), ac209B(), ac210B())
  }

}
