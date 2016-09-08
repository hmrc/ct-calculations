package uk.gov.hmrc.ct.accounts.frs10x.abridged.accountsApproval

import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box._

case class AC8091(value: Option[Boolean]) extends CtBoxIdentifier(name = "Approve accounts approval statement") with CtOptionalBoolean with Input with ValidatableBox[FilingAttributesBoxValueRetriever] {

  override def validate(boxRetriever: FilingAttributesBoxValueRetriever): Set[CtValidation] = Set.empty
}