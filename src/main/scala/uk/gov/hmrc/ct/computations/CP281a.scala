/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP281a(value: Option[Int]) extends CtBoxIdentifier("Losses brought forward from before 01/04/2017")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import losses._
    collectErrors(
      requiredErrorIf(retriever.cpQ17().isTrue && lossReform2017Applies(retriever.cp2()) && !hasValue),
      cannotExistErrorIf(hasValue && (retriever.cpQ17().isFalse || !lossReform2017Applies(retriever.cp2()))),
      validateZeroOrPositiveInteger(this),
      sumOfBreakdownErrors(retriever)
    )
  }

  private def sumOfBreakdownErrors(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cp283a() + retriever.cp288a() != this.orZero) {
      Set(CtValidation(None, "error.CP281a.breakdown.sum.incorrect"))
    }
  }
}

object CP281a {

  def apply(int: Int): CP281a = CP281a(Some(int))
}
