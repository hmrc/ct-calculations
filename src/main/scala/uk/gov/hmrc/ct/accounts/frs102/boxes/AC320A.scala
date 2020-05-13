

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC320A(value: Option[String]) extends CtBoxIdentifier(name = "Basis of measurement and preparation")
                                      with CtOptionalString
                                      with Input
                                      with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]]
                                      with Validators {


  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      cannotExistErrorIf(value.nonEmpty && boxRetriever.ac320().value.contains(true)),
      requiredErrorIf(value.isEmpty && boxRetriever.ac320.value.contains(false)),
      validateStringMaxLength(value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars()
    )
  }
}
