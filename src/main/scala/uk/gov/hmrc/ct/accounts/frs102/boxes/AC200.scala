package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox.StandardCohoTextFieldLimit
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, CtValidation, Input, SelfValidatableBox}

case class AC200(value: Option[String])
extends CtBoxIdentifier(name = "Off balance sheet disclosure note")
with CtOptionalString
with Input
with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]]
 {

  def validateAgainstAC200A(boxRetriever: Frs102AccountsBoxRetriever, boxId: String, value: Option[String]): Set[CtValidation] = {
    (boxRetriever.ac200a(), value) match {
      case (AC200A(Some(true)), None) => validateStringAsMandatory(boxId, AC200(value))
      case (AC200A(Some(true)), Some("")) => validateStringAsMandatory(boxId, AC200(value))
      case (_, _) => Set()
    }
  }

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateAgainstAC200A(boxRetriever, this.boxId, value),
      validateOptionalStringByLength(1, StandardCohoTextFieldLimit)
    )
  }
}