package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.LossesCarriedForwardsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP288(value: Option[Int]) extends CtBoxIdentifier(name = "Losses Carried forward") with CtOptionalInteger

object CP288 extends Calculated[CP288, ComputationsBoxRetriever] with LossesCarriedForwardsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP288 = {
    lossesCarriedForwardsCalculation(cpq17 = fieldValueRetriever.retrieveCPQ17(),
                                     cpq19 = fieldValueRetriever.retrieveCPQ19(),
                                     cpq20 = fieldValueRetriever.retrieveCPQ20(),
                                     cp281 = fieldValueRetriever.retrieveCP281(),
                                     cp118 = fieldValueRetriever.retrieveCP118(),
                                     cp283 = fieldValueRetriever.retrieveCP283(),
                                     cp998 = fieldValueRetriever.retrieveCP998(),
                                     cp287 = fieldValueRetriever.retrieveCP287(),
                                     cato01 = fieldValueRetriever.retrieveCATO01())
  }

}



