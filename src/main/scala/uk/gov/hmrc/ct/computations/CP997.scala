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
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP997(value: Option[Int])
  extends CtBoxIdentifier("Losses from previous AP after 01/04/2017 set against non trading profits this AP")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(retriever.cp281b().isPositive && !hasValue),
      validateZeroOrPositiveInteger(this),
      cp997ExceedsErrors(retriever),
      exceedsNonTradingProfitErrors(retriever)
    )
  }

  private def exceedsNonTradingProfitErrors(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cato01() < this.orZero) {
      Set(CtValidation(Some("CP997"), "error.CP997.exceeds.nonTradingProfit"))
    }
  }

  private def cp997ExceedsErrors(retriever: ComputationsBoxRetriever) = {
    failIf(noTradingProfit(retriever) && (retriever.cato01() < this.orZero + retriever.cp998().orZero)){
      Set(CtValidation(Some("CP997"), "error.CP997.exceeds.nonTradingProfit"))
    }
  }

}

object CP997 {

  def apply(int: Int): CP997 = CP997(Some(int))

}
