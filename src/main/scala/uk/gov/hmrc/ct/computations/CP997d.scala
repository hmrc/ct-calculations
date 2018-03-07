/*
 * Copyright 2018 HM Revenue & Customs
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

case class CP997d(value: Option[Int])
  extends CtBoxIdentifier("Main stream Losses from previous AP after 01/04/2017 set against non trading profits this AP")
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
      validateZeroOrPositiveInteger(this),
      exceedsNonTradingProfitErrors(retriever),
      notAllMainStreamLossSetAgainstNTP(retriever)
    )
  }

  private def exceedsNonTradingProfitErrors(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cato01() < this.orZero + retriever.cp997c.orZero) {
      Set(CtValidation(Some("CP997d"), "error.CP997.exceeds.nonTradingProfit"))
    }
  }

  private def notAllMainStreamLossSetAgainstNTP(retriever: ComputationsBoxRetriever) = {
    failIf(mainStreamLossesNotAllOffsetAgainstNonTradingProfit(retriever)){
      Set(CtValidation(None, "error.CP997d.notAllMainstreamLossesSetAgainstNTP"))
    }
  }
}
