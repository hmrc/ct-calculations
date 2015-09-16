package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.IncomeFromPropertyCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP509(value: Int) extends CtBoxIdentifier(name = "Net Income from property") with CtInteger

object CP509 extends Calculated[CP509, ComputationsBoxRetriever] with IncomeFromPropertyCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP509 = {
    netIncomeFromProperty( cp507 = fieldValueRetriever.retrieveCP507(), cp508 = fieldValueRetriever.retrieveCP508())
  }
}
