/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPAux3(value: Int) extends CtBoxIdentifier("SpecialRatePoolSum/ cars qualifying for special rate ") with CtInteger

object CPAux3 extends Calculated[CPAux3, ComputationsBoxRetriever] with LowEmissionCarsCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CPAux3 =
    CPAux3(getSpecialRatePoolSum(fieldValueRetriever.lec01()))
}
