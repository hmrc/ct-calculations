

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E155(value: Option[Int]) extends CtBoxIdentifier("Held at the end of the period (use accounts figures): Shares in, and loans to, controlled companies") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever]{

  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = validateZeroOrPositiveInteger(this)

}
