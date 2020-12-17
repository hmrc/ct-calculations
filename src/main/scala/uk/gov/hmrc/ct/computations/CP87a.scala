/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.ComputationValidatableBox
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP87a(value: Option[Int]) extends CtBoxIdentifier(name = "Out Of First year allowance claimed")  with CtOptionalInteger with Input with ComputationValidatableBox[ComputationsBoxRetriever] with CtTypeConverters{
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    val max = boxRetriever.cp87Input().orZero
    collectErrors(
      validateZeroOrPositiveInteger(this),
      exceedsMax(value, max)
    )
  }
}
object CP87a {

  def apply(int: Int): CP87a = CP87a(Some(int))

}

