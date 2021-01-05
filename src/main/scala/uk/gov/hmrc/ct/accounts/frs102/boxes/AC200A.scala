/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, CtValidation, Input, SelfValidatableBox}

case class AC200A(value: Option[Boolean])
extends CtBoxIdentifier(name = "Company does have off balance sheet arrangements")
  with CtOptionalBoolean
  with Input
  with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[Boolean]] {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      failIf(value.isEmpty) {
        validateAsMandatory()
      }
    )
  }
  }