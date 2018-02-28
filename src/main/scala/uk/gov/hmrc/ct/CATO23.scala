package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CATO23 (value: Int) extends CtBoxIdentifier(name = "Net Non trade income") with CtInteger

object CATO23 extends Calculated[CATO23, ComputationsBoxRetriever] with NonTradeIncomeCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO23 = {

    NetNonTradeIncomeCalculation(cato01 = fieldValueRetriever.cato01(),
                                 cp997 = fieldValueRetriever.cp997())
  }
}
