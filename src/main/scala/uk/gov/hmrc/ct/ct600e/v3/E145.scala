

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E145(value: Option[Int]) extends CtBoxIdentifier("Held at the end of the period (use accounts figures): UK investments (excluding controlled companies)") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever]{

  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = validateZeroOrPositiveInteger(this)

}
