/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP78(value: Option[Int]) extends CtBoxIdentifier(name = "Written down value brought forward") with CtOptionalInteger with MachineryAndPlantCalculator with Input with SelfValidatableBox[ComputationsBoxRetriever,  Option[Int]] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      validateZeroOrPositiveInteger(),
      sumOfCP78AndCP666(this,boxRetriever.cp666())
    )
  }
}

object CP78 {

  def apply(value: Int): CP78 = CP78(Some(value))
}
