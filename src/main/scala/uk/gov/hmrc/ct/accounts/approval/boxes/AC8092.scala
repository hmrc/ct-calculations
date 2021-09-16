/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.approval.boxes

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC8092(value: Option[String]) extends CtBoxIdentifier(name = "Additional Approver.") with CtOptionalString with Input with ValidatableBox[AccountsBoxRetriever] {

  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    validateStringMaxLength("AC8092", this.value.getOrElse(""), StandardCohoNameFieldLimit) ++ validateCohoOptionalNameField("AC8092", this)
  }
}
