

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ7(value: Option[Boolean]) extends CtBoxIdentifier(name = "Claim trade capital allowances or report balancing charge or incur qualifying expenditure.")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("CPQ7", this)
}
