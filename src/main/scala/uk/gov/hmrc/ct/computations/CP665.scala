/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP665(value: Option[Int]) extends CtBoxIdentifier(name = "outOfFYAExpenditure") with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {

  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      validateZeroOrPositiveInteger()
    )
  }
}

object CP665 {

  def apply(value: Int): CP665 = CP665(Some(value))
}


