/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPAux1(value: Int) extends CtBoxIdentifier("FYAPoolSum") with CtInteger

object CPAux1 extends Calculated[CPAux1, ComputationsBoxRetriever] with LowEmissionCarsCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CPAux1 =
    CPAux1(getFYAPoolSum(fieldValueRetriever.lec01()))
}
