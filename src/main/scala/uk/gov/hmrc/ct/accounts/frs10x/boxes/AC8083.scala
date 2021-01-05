/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC8083(value: Option[Boolean]) extends CtBoxIdentifier(name = "The directors acknowledge their responsibilities for complying with the requirements of the Act with respect to accounting records and the preparation of accounts.")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateBooleanAsTrue("AC8083", this)
    )
  }
  
}
