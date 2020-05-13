

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC451(value: Option[Int]) extends CtBoxIdentifier(name = "Fixed assets (previous PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs105AccountsBoxRetriever] {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}
