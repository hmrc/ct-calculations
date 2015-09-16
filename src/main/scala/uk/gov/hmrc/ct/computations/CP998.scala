package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.LossesSetAgainstOtherProfitsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP998(value: Option[Int]) extends CtBoxIdentifier(name = "Losses this AP set against other profits this AP") with CtOptionalInteger

object CP998 extends Calculated[CP998, ComputationsBoxRetriever] with LossesSetAgainstOtherProfitsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP998 = {
    calculateLossesSetAgainstProfits(cato01 = fieldValueRetriever.retrieveCATO01(),
                                     cp118 = fieldValueRetriever.retrieveCP118(),
                                     cpq19 = fieldValueRetriever.retrieveCPQ19())
  }

}

