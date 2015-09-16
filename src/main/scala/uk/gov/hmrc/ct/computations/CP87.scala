package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TotalFirstYearAllowanceClaimedCalculation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP87(value: Int) extends CtBoxIdentifier(name = "Total first year allowance claimed") with CtInteger

object CP87 extends Calculated[CP87, ComputationsBoxRetriever] with TotalFirstYearAllowanceClaimedCalculation{

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP87 = {
    totalFirstYearAllowanceClaimedCalculation(cp85 = fieldValueRetriever.retrieveCP85(), cp86 = fieldValueRetriever.retrieveCP86())
  }
}


