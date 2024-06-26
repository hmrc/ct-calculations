/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
