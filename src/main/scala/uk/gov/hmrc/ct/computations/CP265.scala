

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class CP265(value: Int) extends CtBoxIdentifier("Profits before other deductions and reliefs (box 21)") with CtInteger

object CP265 extends Calculated[CP265, ComputationsBoxRetriever] with CtTypeConverters {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP265 = {
    CP265(boxRetriever.cp293() + boxRetriever.cp283b())
  }
}
