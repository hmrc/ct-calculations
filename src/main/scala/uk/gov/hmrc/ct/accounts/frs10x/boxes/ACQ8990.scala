

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever
import uk.gov.hmrc.ct.box._

case class ACQ8990(value: Option[Boolean]) extends CtBoxIdentifier(name = "Profit and loss statement.")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs10xDormancyBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs10xDormancyBoxRetriever): Set[CtValidation] = {
    failIf(boxRetriever.profitAndLossStatementRequired().value) {
      validateBooleanAsTrue("ACQ8990", this)
    }
  }
}
