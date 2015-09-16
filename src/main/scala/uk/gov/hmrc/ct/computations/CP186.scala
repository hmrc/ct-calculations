package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP186(value: Option[Int]) extends CtBoxIdentifier(name = "Total Allowances") with CtOptionalInteger

object CP186 extends Calculated[CP186, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP186 = {
    computeTotalAllowancesClaimed(cpq8 = fieldValueRetriever.retrieveCPQ8(),
                                  cp87 = fieldValueRetriever.retrieveCP87(),
                                  cp88 = fieldValueRetriever.retrieveCP88(),
                                  cp89 = fieldValueRetriever.retrieveCP89(),
                                  cp90 = fieldValueRetriever.retrieveCP90())
  }

}


