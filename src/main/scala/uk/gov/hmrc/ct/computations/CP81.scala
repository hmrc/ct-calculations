package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP81(value: Int) extends CtBoxIdentifier(name = "Total expenditure qualifying for the first year allowance (FYA)") with CtInteger

object CP81 extends Calculated[CP81, ComputationsBoxRetriever] with TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP81 = {
    totalExpenditureQualifyingForTheFirstYearAllowance(cp79 = fieldValueRetriever.retrieveCP79(),
                                                       cp80 = fieldValueRetriever.retrieveCP80())
  }

}
