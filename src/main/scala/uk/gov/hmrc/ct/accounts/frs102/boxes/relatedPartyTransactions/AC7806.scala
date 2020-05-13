

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC7806(value: Option[String]) extends CtBoxIdentifier(name = "Additional information")
  with CtOptionalString
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      validateStringMaxLength("AC7806", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars("AC7806", this)    )
  }
}
