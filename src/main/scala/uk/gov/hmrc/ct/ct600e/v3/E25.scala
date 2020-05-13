

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBoolean}
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E25(value: Option[Boolean]) extends CtBoxIdentifier("Some of the income and gains may not be exempt or have not been applied for charitable or qualifying purposes only, and I have completed form CT600") with CtOptionalBoolean

object E25 extends Calculated[E25, CT600EBoxRetriever] {
  override def calculate(boxRetriever: CT600EBoxRetriever): E25 = {

    E25(boxRetriever.e20().inverse)
  }
}
