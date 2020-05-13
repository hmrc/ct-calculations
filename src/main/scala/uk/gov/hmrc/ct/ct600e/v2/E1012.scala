

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBoolean}
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E1012(value: Option[Boolean]) extends CtBoxIdentifier("Some not only charitable") with CtOptionalBoolean

object E1012 extends Calculated[E1012, CT600EBoxRetriever] {
  override def calculate(boxRetriever: CT600EBoxRetriever): E1012 = {
    E1012(boxRetriever.e1011().inverse)
  }
}
