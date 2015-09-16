package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP92(value: Option[Int]) extends CtBoxIdentifier(name = "Written down value brought forward") with CtOptionalInteger

object CP92 extends Calculated[CP92, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP92 = {
    writtenDownValue(cpq8 = boxRetriever.retrieveCPQ8(),
                     cpq10 = boxRetriever.retrieveCPQ10(),
                     cp78 = boxRetriever.retrieveCP78(),
                     cp81 = boxRetriever.retrieveCP81(),
                     cp82 = boxRetriever.retrieveCP82(),
                     cp84 = boxRetriever.retrieveCP84(),
                     cp186 = boxRetriever.retrieveCP186(),
                     cp91 = boxRetriever.retrieveCP91())
  }
}


