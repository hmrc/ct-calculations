package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TotalAdditionsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP54(value: Int) extends CtBoxIdentifier(name = "Total Additions") with CtInteger

object CP54 extends Calculated[CP54, ComputationsBoxRetriever] with TotalAdditionsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP54 = {
    totalAdditionsCalculation(cp503 = fieldValueRetriever.retrieveCP503(),
                              cp46 = fieldValueRetriever.retrieveCP46(),
                              cp47 = fieldValueRetriever.retrieveCP47(),
                              cp48 = fieldValueRetriever.retrieveCP48(),
                              cp49 = fieldValueRetriever.retrieveCP49(),
                              cp51 = fieldValueRetriever.retrieveCP51(),
                              cp52 = fieldValueRetriever.retrieveCP52(),
                              cp53 = fieldValueRetriever.retrieveCP53())
  }
}