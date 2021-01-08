/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class AP1(inputValue: Option[Int], defaultValue: Option[Int]) extends CtBoxIdentifier(name = "Turnover apportioned before accounting period") with CtOptionalInteger with InputWithDefault[Int] with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    validateZeroOrPositiveInteger(this)
  }
}

object AP1 {

  def apply(inputValue: Option[Int]): AP1 = AP1(inputValue = inputValue, defaultValue = None)
}
