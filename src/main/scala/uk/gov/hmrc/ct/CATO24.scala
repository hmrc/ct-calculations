/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class CATO24(value: Option[Boolean]) extends CtBoxIdentifier(name = "Off payroll working")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    validateBooleanAsMandatory("CATO24", this)
  }
}
