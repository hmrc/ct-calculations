/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class CPAux2(value: Int) extends CtBoxIdentifier("MainRatePoolSum/ cars qualifying for main rate") with CtInteger // cars qualifying

object CPAux2 extends Calculated[CPAux2, ComputationsBoxRetriever] with LowEmissionCarsCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CPAux2 =
    CPAux2(getMainRatePoolSum(fieldValueRetriever.lec01()))
}
