/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class CP672a(value: Option[Int]) extends CtBoxIdentifier(name = "Out Of Proceeds from disposals from main pool")  with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    val max = boxRetriever.cp672().orZero
    collectErrors(
      validateZeroOrPositiveInteger(),
      exceedsMax(value, max)
    )
  }
}
object CP672a {

  def apply(value: Int): CP672a = CP672a(Some(value))
}


