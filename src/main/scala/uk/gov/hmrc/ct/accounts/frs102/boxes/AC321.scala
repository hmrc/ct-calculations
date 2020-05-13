

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC321(value: Option[String]) extends CtBoxIdentifier(name = "Turnover policy")
                                      with CtOptionalString
                                      with Input
                                      with ValidatableBox[Frs102AccountsBoxRetriever]
                                      with Validators {


  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateStringMaxLength("AC321", value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars("AC321", this)
    )
  }
}
