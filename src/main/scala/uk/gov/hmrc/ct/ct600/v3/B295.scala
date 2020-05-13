

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, CtTypeConverters}
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B295(value: Int) extends CtBoxIdentifier(name = "Total of deductions and reliefs") with CtInteger

object B295 extends Calculated[B295, CT600BoxRetriever] with CtTypeConverters {
  override def calculate(boxRetriever: CT600BoxRetriever): B295 = {
    B295(boxRetriever.b275() + boxRetriever.b285())
  }
}
