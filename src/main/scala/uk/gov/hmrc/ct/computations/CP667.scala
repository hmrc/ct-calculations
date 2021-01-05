/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP667(value: Option[Int]) extends CtBoxIdentifier(name = "Proceeds from disposals from special rate pool")  with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      cannotExistErrorIf(hasValue && boxRetriever.cpQ8().isTrue),
      validateZeroOrPositiveInteger()
    )
  }
}

object CP667 {

  def apply(value: Int): CP667 = CP667(Some(value))
}
