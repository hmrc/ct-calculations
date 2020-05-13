

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.PoolPercentageCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CATO21(value: BigDecimal) extends CtBoxIdentifier(name = "Apportioned Main Rate") with CtBigDecimal with NotInPdf

object CATO21 extends Calculated[CATO21, ComputationsBoxRetriever]  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO21 = CATO21(PoolPercentageCalculator().apportionedMainRate(fieldValueRetriever.cp1, fieldValueRetriever.cp2))
}
