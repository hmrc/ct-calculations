package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.SummaryCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP305(value: Int) extends CtBoxIdentifier(name = "Qualifying charitable Donation") with CtInteger

object CP305 extends Calculated[CP305, ComputationsBoxRetriever] with SummaryCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP305 = {
   calculateQualifyingCharitableDonations(fieldValueRetriever.retrieveCP301(), fieldValueRetriever.retrieveCP302())
  }

}
