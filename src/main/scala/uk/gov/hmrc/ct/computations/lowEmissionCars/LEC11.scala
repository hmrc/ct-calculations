/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.lowEmissionCars

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class LEC11(value: Boolean) extends CtBoxIdentifier("Disposals Less Than Special Rate Pool") with CtBoolean

object LEC11 extends Calculated[LEC11, ComputationsBoxRetriever] with LowEmissionCarsCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): LEC11 =
    LEC11(disposalsLessThanSpecialRatePool(fieldValueRetriever.lec01(),
      fieldValueRetriever.cp666(),
      fieldValueRetriever.cp667()
    ))
}
