

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC7995(value: Option[String]) extends CtBoxIdentifier(name = "Commitments by way of guarantee note") with CtOptionalString
with Input
with SelfValidatableBox[Frs105AccountsBoxRetriever, Option[String]] {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever) = {

    import boxRetriever._
    collectErrors (
      cannotExistErrorIf(value.nonEmpty && ac7991().isFalse),

      failIf (boxRetriever.ac7991().isTrue) (
        collectErrors (
          validateStringAsMandatory(),
          validateOptionalStringByLength(1, StandardCohoTextFieldLimit),
          validateCoHoStringReturnIllegalChars()
        )
      )
    )
  }
}
