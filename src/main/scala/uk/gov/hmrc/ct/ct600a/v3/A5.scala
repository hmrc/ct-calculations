

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A5(value: Option[Boolean]) extends CtBoxIdentifier(name = "Were any loans written off or released during this period?") with CtOptionalBoolean with Input with ValidatableBox[CT600ABoxRetriever] {

  override def validate(boxRetriever: CT600ABoxRetriever): Set[CtValidation] = {
    failIf(boxRetriever.lpq04().orFalse) {
      validateBooleanAsMandatory("A5", this)
    }
  }

}
