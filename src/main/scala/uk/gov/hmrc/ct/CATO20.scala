package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, NotInPdf}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CATO20(value: Int) extends CtBoxIdentifier(name = "UnclaimedAIA_FYA") with CtInteger with NotInPdf

object CATO20 extends Calculated[CATO20, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO20 = {
    unclaimedAIAFirstYearAllowance(cp81 = fieldValueRetriever.retrieveCP81(),
                                            cp83 = fieldValueRetriever.retrieveCP83(),
                                            cp87 = fieldValueRetriever.retrieveCP87(),
                                            cp88 = fieldValueRetriever.retrieveCP88(),
                                            cpAux1 = fieldValueRetriever.retrieveCPAux1())
  }
}