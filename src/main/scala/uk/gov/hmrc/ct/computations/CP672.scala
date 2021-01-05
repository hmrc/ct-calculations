/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP672(value: Option[Int]) extends CtBoxIdentifier(name = "Proceeds from disposals from main pool")  with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      cannotExistErrorIf(hasValue && boxRetriever.cpQ8().isTrue),
      validateZeroOrPositiveInteger()
    )
  }
}

object CP672 {

  def apply(value: Int): CP672 = CP672(Some(value))
}
