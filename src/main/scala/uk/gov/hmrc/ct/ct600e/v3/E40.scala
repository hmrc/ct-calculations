

package uk.gov.hmrc.ct.ct600e.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalDate, Input, ValidatableBox}
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E40(value: Option[LocalDate]) extends CtBoxIdentifier("Claiming exemption date") with CtOptionalDate with Input  with ValidatableBox[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever) = {
    validateAsMandatory(this) ++ validateDateAsBetweenInclusive("E40", this, boxRetriever.e4().value, DateHelper.now())
  }
}
