

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC5133(value: Option[String]) extends CtBoxIdentifier(name = "Tangible assets additional information") with CtOptionalString
with Input
with ValidatableBox[Frs102AccountsBoxRetriever]
with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      failIf (boxRetriever.ac44.hasValue) (
        collectErrors (
          validateOptionalStringByLength("AC5133", this, 1, StandardCohoTextFieldLimit),
          validateCoHoStringReturnIllegalChars("AC5133", this)
        )
      )
    )
  }
}
