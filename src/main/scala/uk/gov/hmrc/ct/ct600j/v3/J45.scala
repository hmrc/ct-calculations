package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class J45(value: Option[String]) extends CtBoxIdentifier("Scheme reference number") with CtOptionalString with Input with ValidatableBox[CT600BoxRetriever] {

  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    if(boxRetriever.retrieveJ40().value.isEmpty) {
      validateStringAsBlank("J45A", this)
    } else {
      validateStringByRegex("J45A", this, taxAvoidanceSchemeNumberRegex)
    }
  }

}
