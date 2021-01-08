/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.lowEmissionCars

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class LEC12(value: Boolean) extends CtBoxIdentifier("Disposals Exceed Main Rate Pool") with CtBoolean

object LEC12 extends Calculated[LEC12, ComputationsBoxRetriever] with LowEmissionCarsCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): LEC12 =
    LEC12(disposalsExceedsMainRatePool(fieldValueRetriever.lec01(),
      fieldValueRetriever.cp78(),
      fieldValueRetriever.cp82(),
      fieldValueRetriever.cp672()
    ))
}
