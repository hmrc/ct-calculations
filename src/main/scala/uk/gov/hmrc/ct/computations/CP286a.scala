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
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP286a (value: Option[Int])
  extends CtBoxIdentifier("Later to current stuff...")
    with CtOptionalInteger
    with Input
    with ValidatableBox[ComputationsBoxRetriever] with NorthernIrelandRateValidation with TradingLossesValidation{

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(value.isEmpty && boxRetriever.cpQ18.isTrue),
      cannotExistErrorIf(value.nonEmpty && !boxRetriever.cpQ18().orFalse),
      requiredErrorIf(
          mayHaveNirLosses(boxRetriever) &&
          !hasValue),
      validateIntegerRange("CP286", this, 0, boxRetriever.cp286().orZero),
      sumOfBreakDownError(boxRetriever)
    )
  }

  private def sumOfBreakDownError(retriever: ComputationsBoxRetriever) = {
    failIf(this.orZero + retriever.cp286b().orZero > retriever.cp286().orZero){
      Set(CtValidation(None, "error.exceeds.tradingProfit.error"))

    }
  }

}

object CP286a {

  def apply(int: Int): CP286a = CP286a(Some(int))
}
