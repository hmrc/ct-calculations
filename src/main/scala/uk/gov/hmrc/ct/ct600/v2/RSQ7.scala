

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v2.retriever.ReturnStatementsBoxRetriever
import uk.gov.hmrc.ct.ct600.v2.validation.RSQ7MutuallyExclusiveWithRSQ8

case class RSQ7(value: Option[Boolean]) extends CtBoxIdentifier
  with CtOptionalBoolean with Input with ValidatableBox[ReturnStatementsBoxRetriever] with RSQ7MutuallyExclusiveWithRSQ8 {

  override def validate(boxRetriever: ReturnStatementsBoxRetriever): Set[CtValidation] = {
    validateAsMandatory(this) ++ validateMutualExclusivity(boxRetriever)
  }
}
