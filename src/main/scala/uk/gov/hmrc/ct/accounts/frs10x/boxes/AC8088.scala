/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC8088(value: Option[Boolean]) extends CtBoxIdentifier(name = "I agree to the legal statements - include them with my balance sheet\t")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateBooleanAsTrue("AC8088", this)
    )
  }
  
}
