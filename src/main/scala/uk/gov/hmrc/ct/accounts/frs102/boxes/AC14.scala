/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC14(value: Option[Int]) extends CtBoxIdentifier(name = "Cost of sales (current PoA)")
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
