/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC213(value: Option[Int]) extends CtBoxIdentifier(name = "Valuation of all tangible assets that have been transferred during the period")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value)
    )
  }
}

object AC213 extends Calculated[AC213, FullAccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC213 = {
    import boxRetriever._
    calculateAC213(
      ac213A(),
      ac213B(),
      ac213C(),
      ac213D(),
      ac213E()
    )
  }
}
