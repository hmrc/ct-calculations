

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP500(value: Int) extends CtBoxIdentifier(name = "Total Expenses") with CtInteger

object CP500 extends Calculated[CP500, ComputationsBoxRetriever] {

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP500 = {
    CP500(boxRetriever.cp39().value - boxRetriever.cp40().value)
  }

}
