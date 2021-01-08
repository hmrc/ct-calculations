/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.lowEmissionCars

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class LEC10(value: Boolean) extends CtBoxIdentifier("Disposals Exceed Special Rate Pool") with CtBoolean

object LEC10 extends Calculated[LEC10, ComputationsBoxRetriever] with LowEmissionCarsCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): LEC10 =
    LEC10(disposalsExceedsSpecialRatePool(fieldValueRetriever.lec01(),
      fieldValueRetriever.cp666(),
      fieldValueRetriever.cp667()
    ))
}
