/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP27(value: Option[Int]) extends CtBoxIdentifier(name = "Bank, credit card and other financial charges") with CtOptionalInteger with Input
  with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    validateZeroOrPositiveInteger(this)
  }
}

object CP27 {

  def apply(int: Int): CP27 = CP27(Some(int))
}
