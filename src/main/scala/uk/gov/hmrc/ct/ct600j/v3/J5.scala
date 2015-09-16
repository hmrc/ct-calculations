package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class J5(value: Option[String]) extends CtBoxIdentifier("Scheme reference number") with CtOptionalString with Input with ValidatableBox[CT600BoxRetriever] {

  val boxNumber = "J5"

  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    if(boxRetriever.retrieveB65().value.getOrElse(false)) {
      validateStringAsMandatory(boxNumber, this) ++ validateStringByRegex(boxNumber, this, taxAvoidanceSchemeNumberRegex)
    } else {
      Set()
    }
  }

}
