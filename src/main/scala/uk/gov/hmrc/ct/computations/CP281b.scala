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
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP281b(value: Option[Int])
  extends CtBoxIdentifier("Losses brought forward from on or after 01/04/2017")
    with CtOptionalInteger
    with Input
    with ValidatableBox[ComputationsBoxRetriever]
    with TradingLossesValidation with NorthernIrelandRateValidation {
  import losses._

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(retriever.cpQ17().isTrue && lossReform2017Applies(retriever.cp2()) && !hasValue),
      cannotExistErrorIf(hasValue && (retriever.cpQ17().isFalse || !lossReform2017Applies(retriever.cp2()))),
      validateZeroOrPositiveInteger(this),
      sumOfBreakdownErrors(retriever)/*,
      nirSumOfBreakdownErrors(retriever)*/
    )
  }

  private def sumOfBreakdownErrors(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cp283b() + retriever.cp288b() + retriever.chooseCp997() != this.orZero) {
      Set(CtValidation(None, "error.CP281b.breakdown.sum.incorrect"))
    }
  }
/*  private def nirSumOfBreakdownErrors(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cp281c() + retriever.cp281d() <= this.orZero) {
      Set(CtValidation(None, "error.CP281b.breakdown.sumNIR.incorrect"))
    }
  }*/

}

object CP281b extends Calculated[CP281b, ComputationsBoxRetriever] {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP281b = {
    import boxRetriever._
    CP281b(cp281().value.map { allLosses =>
      allLosses - cp281a().orZero
    })
  }

  def apply(int: Int): CP281b = CP281b(Some(int))
}
