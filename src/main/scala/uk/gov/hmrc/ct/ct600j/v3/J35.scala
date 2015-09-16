package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class J35(value: Option[String]) extends CtBoxIdentifier("Scheme reference number") with CtOptionalString with Input with ValidatableBox[CT600BoxRetriever] {

  val boxNumber = "J35"

  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    if(boxRetriever.retrieveJ30().value.isEmpty) {
      validateStringAsBlank(boxNumber, this)
    } else {
      validateStringByRegex(boxNumber, this, taxAvoidanceSchemeNumberRegex)
    }
  }

}
