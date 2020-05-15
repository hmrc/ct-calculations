/*
 * Copyright 2020 HM Revenue & Customs
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP283b(value: Option[Int]) extends CtBoxIdentifier("Losses brought forward from on or after 01/04/2017 used against trading profit")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(boxRetriever.cp281b().isPositive && !hasValue && boxRetriever.cp117().isPositive),
      validateZeroOrPositiveInteger(this),
      sumOfBroughtForwardErrors(boxRetriever),
      sumOfBreakDownError(boxRetriever),
      sumError(boxRetriever)
    )
  }

  private def sumError(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cp283d().orZero + retriever.cp283c().orZero > this.orZero) {
      Set(CtValidation(None, "error.CP283b.breakdown.sum.error"))
    }
  }

  private def sumOfBreakDownError(retriever: ComputationsBoxRetriever) = {
    failIf(this.orZero > retriever.cp117().value) {
      Set(CtValidation(None, "error.CP283b.exceeds.tradingProfit.error"))
    }
  }
}


object CP283b {

  def apply(int: Int): CP283b = CP283b(Some(int))
}
