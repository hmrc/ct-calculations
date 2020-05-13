

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.formats._

case class CP297(value: Option[Int]) extends CtBoxIdentifier("Total Structure and Building Allowance Claimed") with CtOptionalInteger

object CP297 extends Calculated[CP297, ComputationsBoxRetriever] with SBACalculator {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP297 = {
    val result  = boxRetriever.sba01().buildings.map {
      building => building.claim
    }

    CP297(sumAmount(result))

  }
}



