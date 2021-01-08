/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP283a(value: Option[Int]) extends CtBoxIdentifier("Losses brought forward from before 01/04/2017 used against trading profit")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(boxRetriever.cp281a().isPositive && !hasValue && boxRetriever.cp117().isPositive),
      validateZeroOrPositiveInteger(this),
      sumOfBroughtForwardErrors(boxRetriever)
    )
  }
}

object CP283a {

  def apply(int: Int): CP283a = CP283a(Some(int))
}
