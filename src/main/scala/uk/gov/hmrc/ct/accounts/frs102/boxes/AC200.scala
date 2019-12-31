package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.validation.OffBalanceSheetArrangementsValidator
import uk.gov.hmrc.ct.box.ValidatableBox.StandardCohoTextFieldLimit
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, CtValidation, Input, SelfValidatableBox}

case class AC200(value: Option[String])
extends CtBoxIdentifier(name = "Off balance sheet disclosure note")
with CtOptionalString
with Input
with SelfValidatableBox[Frs105AccountsBoxRetriever, Option[String]]
with OffBalanceSheetArrangementsValidator {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateAgainstAC7999a(boxRetriever, this.boxId, value),
      validateOptionalStringByLength(1, StandardCohoTextFieldLimit)
}