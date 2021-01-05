/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP997c(value: Option[Int]) extends CtBoxIdentifier("NIR Losses from previous AP after 01/04/2017 set against non-trading profit this AP")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with NorthernIrelandRateValidation {

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(retriever.cp281b().isPositive &&
                      mayHaveNirLosses(retriever) &&
                      retriever.cato01().value > 0 &&
                      !hasValue),
      cannotExistErrorIf(!mayHaveNirLosses(retriever) &&
                         hasValue),
      validateZeroOrPositiveInteger(this),
      exceedsNonTradingProfitErrors(retriever)
    )
  }

  private def exceedsNonTradingProfitErrors(retriever: ComputationsBoxRetriever) = {
    val cp997e = CP997e(this, retriever.cp1(), retriever.cp2(), retriever.cpQ19())
    failIf(retriever.cato01() < cp997e.orZero + retriever.cp997d().orZero) {
      Set(CtValidation(Some("CP997c"), "error.CP997.exceeds.nonTradingProfit"))
    }
  }
}
