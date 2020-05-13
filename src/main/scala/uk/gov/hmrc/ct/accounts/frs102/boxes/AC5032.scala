

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC5032(value: Option[String]) extends CtBoxIdentifier(name = "Profit/(loss) before tax note")
                                      with CtOptionalString
                                      with Input
                                      with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[String]] {


  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      cannotExistErrorIf(hasValue && !boxRetriever.ac32().hasValue && !boxRetriever.ac33().hasValue),
      validateStringMaxLength(value.getOrElse(""), StandardCohoTextFieldLimit),
      validateCoHoStringReturnIllegalChars()
    )
  }
}
