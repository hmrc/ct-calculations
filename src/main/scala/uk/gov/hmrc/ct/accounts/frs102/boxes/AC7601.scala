

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC7601(value: Option[String]) extends CtBoxIdentifier(name = "Changes in presentation and prior period adjustments") with CtOptionalString
with Input
with ValidatableBox[Frs102AccountsBoxRetriever]
with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      cannotExistErrorIf(!boxRetriever.ac7600().orFalse && value.nonEmpty),

      failIf (boxRetriever.ac7600().orFalse) (
        collectErrors (
          validateStringAsMandatory("AC7601", this),
          validateOptionalStringByLength("AC7601", this, 1, StandardCohoTextFieldLimit),
          validateCoHoStringReturnIllegalChars("AC7601", this)
        )
      )
    )
  }
}
