package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, NotInPdf, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.calculations.PoolPercentageCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class CATO22(value: BigDecimal) extends CtBoxIdentifier(name = "Apportioned Special Rate") with CtBigDecimal with NotInPdf

object CATO22 extends Calculated[CATO22, ComputationsBoxRetriever]  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO22 = CATO22(PoolPercentageCalculator().apportionedSpecialRate(fieldValueRetriever.retrieveCP1, fieldValueRetriever.retrieveCP2))
}
