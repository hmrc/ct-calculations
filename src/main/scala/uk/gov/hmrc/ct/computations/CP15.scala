/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP15(value: Option[Int]) extends CtBoxIdentifier(name = "Directors Pension") with CtOptionalInteger with Input
  with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    validateZeroOrPositiveInteger(this)
  }
}

object CP15 {

  def apply(int: Int): CP15 = CP15(Some(int))
}
