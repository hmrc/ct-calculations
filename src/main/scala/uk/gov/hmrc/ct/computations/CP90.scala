package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP90(value: Option[Int]) extends CtBoxIdentifier(name = "Balance Allowance") with CtOptionalInteger

object CP90 extends Calculated[CP90, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP90 = {
    computeBalanceAllowance(cpq8 = fieldValueRetriever.retrieveCPQ8(),
                            cp78 = fieldValueRetriever.retrieveCP78(),
                            cp81 = fieldValueRetriever.retrieveCP81(),
                            cp82 = fieldValueRetriever.retrieveCP82(),
                            cp84 = fieldValueRetriever.retrieveCP84(),
                            cp91 = fieldValueRetriever.retrieveCP91Input())
  }
}


