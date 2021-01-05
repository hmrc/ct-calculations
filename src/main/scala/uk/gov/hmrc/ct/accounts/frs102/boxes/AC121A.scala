/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC121A(value: Option[Int]) extends CtBoxIdentifier(name = "Intangible assets - Goodwill - Amortisation - Amortisation at [POA END]")
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

object AC121A extends Calculated[AC121A, FullAccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC121A = {
    import boxRetriever._
    calculateAC121A(ac118A(), ac119A(), ac120A(), ac211A())
  }

}
