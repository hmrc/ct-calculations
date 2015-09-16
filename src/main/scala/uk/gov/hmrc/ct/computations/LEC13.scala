package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class LEC13(value: Boolean) extends CtBoxIdentifier("Disposals Less Than Main Rate Pool") with CtBoolean

object LEC13 extends Calculated[LEC13, ComputationsBoxRetriever] with LowEmissionCarsCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): LEC13 =
    LEC13(disposalsLessThanMainRatePool(fieldValueRetriever.retrieveLEC01(),
      fieldValueRetriever.retrieveCP78(),
      fieldValueRetriever.retrieveCP82(),
      fieldValueRetriever.retrieveCP672()
    ))
}