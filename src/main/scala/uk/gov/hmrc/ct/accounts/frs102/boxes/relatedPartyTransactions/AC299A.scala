/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC299A(value: Option[String]) extends CtBoxIdentifier(name = "Name of related party")
  with CtOptionalString
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      validateAsMandatory(this),
      validateStringMaxLength("AC299A", value.getOrElse(""), StandardCohoNameFieldLimit),
      validateCohoOptionalNameField("AC299A", this)
    )
  }
}
