/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP666(value: Option[Int]) extends CtBoxIdentifier(name = "Written down value of special rate pool brought forward") with CtOptionalInteger with MachineryAndPlantCalculator with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      validateZeroOrPositiveInteger(),
      sumOfCP78AndCP666(boxRetriever.cp78(),this)
    )
  }
}

object CP666 {

  def apply(value: Int): CP666 = CP666(Some(value))
}
