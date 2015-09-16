package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP91(value: Option[Int]) extends CtBoxIdentifier(name = "Balancing charge") with CtOptionalInteger

object CP91 extends Calculated[CP91, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP91 = {
    computeBalancingCharge(cpq8 = fieldValueRetriever.retrieveCPQ8(),
                           cp78 = fieldValueRetriever.retrieveCP78(),
                           cp81 = fieldValueRetriever.retrieveCP81(),
                           cp82 = fieldValueRetriever.retrieveCP82(),
                           cp84 = fieldValueRetriever.retrieveCP84(),
                           cp91 = fieldValueRetriever.retrieveCP91Input())
  }
}


