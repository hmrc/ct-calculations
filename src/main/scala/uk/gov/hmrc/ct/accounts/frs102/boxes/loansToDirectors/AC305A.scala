

package uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC305A(value: Option[String]) extends CtBoxIdentifier(name = "Description of Loan")
  with CtOptionalString
  with Input
  with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      validateAsMandatory(),
      validateOptionalStringByLength(1, 250),
      validateCoHoStringReturnIllegalChars()
    )
  }
}
