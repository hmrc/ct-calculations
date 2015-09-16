package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.NetProfitsChargeableToCtCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP295(value: Int) extends CtBoxIdentifier(name = "Profits chargeable to CT") with CtInteger

object CP295 extends Calculated[CP295, ComputationsBoxRetriever] with NetProfitsChargeableToCtCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP295 = {
    calculateNetProfitsChargeableToCt(fieldValueRetriever.retrieveCP293(),
      fieldValueRetriever.retrieveCP294(),
      fieldValueRetriever.retrieveCP999())
  }

}
