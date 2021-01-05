/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC116(value: Option[Int]) extends CtBoxIdentifier(name = "Disposals")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators
  with Debit {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}

object AC116 extends Calculated[AC116, FullAccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC116 = {
    import boxRetriever._
    calculateAC116(ac116A(), ac116B())
  }

}
