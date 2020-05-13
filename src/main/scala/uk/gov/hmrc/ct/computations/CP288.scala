

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.LossesCarriedForwardsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP288(value: Option[Int]) extends CtBoxIdentifier(name = "Losses Carried forward") with CtOptionalInteger

object CP288 extends Calculated[CP288, ComputationsBoxRetriever] with LossesCarriedForwardsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP288 = {
    lossesCarriedForwardsCalculation(
      cp281 = fieldValueRetriever.cp281(),
      cp118 = fieldValueRetriever.cp118(),
      cp283 = fieldValueRetriever.cp283(),
      cp998 = fieldValueRetriever.cp998(),
      cp287 = fieldValueRetriever.cp287(),
      cp997 = fieldValueRetriever.cp997(),
      cp997d = fieldValueRetriever.cp997d(),
      cp997c = fieldValueRetriever.cp997c()
    )
  }

}
