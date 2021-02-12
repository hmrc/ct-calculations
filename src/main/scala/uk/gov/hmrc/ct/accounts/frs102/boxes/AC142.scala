/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC142(value: Option[Int]) extends CtBoxIdentifier(name = "Creditors: bank loans and overdrafts (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[FullAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}
