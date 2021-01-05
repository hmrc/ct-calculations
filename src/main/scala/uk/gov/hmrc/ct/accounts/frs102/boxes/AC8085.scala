/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC8085(value: Option[Boolean]) extends CtBoxIdentifier(name = "These accounts have been prepared in accordance with the provisions applicable to companies subject to the small companies regime.")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    collectErrors(
      validateAsMandatory(this)
    )
  }
  
}
