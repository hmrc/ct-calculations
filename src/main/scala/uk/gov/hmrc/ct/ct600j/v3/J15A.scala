package uk.gov.hmrc.ct.ct600j.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class J15A(value: Option[LocalDate]) extends CtBoxIdentifier("Accounting period in which the expected advantage arises") with CtOptionalDate with Input with ValidatableBox[CT600BoxRetriever] {

  val boxNumber = "J15A"

  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = {
    if(boxRetriever.retrieveJ10A().value.isEmpty) {
      validateDateAsBlank(boxNumber, this)
    } else {
      if(boxRetriever.retrieveJ15().value.isDefined) {
        validateDateAsMandatory(boxNumber, this)
      } else {
        Set()
      }
    }
  }

}
