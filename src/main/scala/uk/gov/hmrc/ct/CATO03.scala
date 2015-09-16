package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.WritingDownAllowanceCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CATO03(value: Int) extends CtBoxIdentifier(name = "Write down allowance limit") with CtInteger

object CATO03 extends Calculated[CATO03, ComputationsBoxRetriever] with WritingDownAllowanceCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO03 = {
    super.calculate(cp1 = fieldValueRetriever.retrieveCP1(),
                    cp2 = fieldValueRetriever.retrieveCP2(),
                    cp78 = fieldValueRetriever.retrieveCP78(),
                    cp82 = fieldValueRetriever.retrieveCP82(),
                    cp84 = fieldValueRetriever.retrieveCP84(),
                    cp88 = fieldValueRetriever.retrieveCP88())
  }
}

