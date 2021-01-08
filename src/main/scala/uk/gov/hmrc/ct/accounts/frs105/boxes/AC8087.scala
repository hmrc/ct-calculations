/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC8087(value: Option[Boolean]) extends CtBoxIdentifier(name = "The directors acknowledge their responsibilities for complying with the requirements of the Act with respect to accounting records and the preparation of accounts.")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with Validators {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    collectErrors(
      validateAsMandatory(this)
    )
  }
  
}
