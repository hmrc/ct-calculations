package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TotalDonationsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP999(value: Int) extends CtBoxIdentifier(name = "Total Donations") with CtInteger

object CP999 extends Calculated[CP999, ComputationsBoxRetriever] with TotalDonationsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP999 = {
    calculateTotalDonations(cpq21 = fieldValueRetriever.retrieveCPQ21(),
      cp301 = fieldValueRetriever.retrieveCP301(),
      cp302 = fieldValueRetriever.retrieveCP302())
  }
}
