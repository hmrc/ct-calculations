/*
 * Copyright 2021 HM Revenue & Customs
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

case class CP283c(value: Option[Int])
  extends CtBoxIdentifier("NIR Losses brought forward from on or after 01/04/2017 used against trading profit")
    with CtOptionalInteger
    with Input
    with ValidatableBox[ComputationsBoxRetriever] with NorthernIrelandRateValidation with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(
        boxRetriever.cp283b().isPositive &&
          mayHaveNirLosses(boxRetriever) &&
          !hasValue),
      validateIntegerRange("CP283c", this, 0, boxRetriever.cp283b().orZero),
      lossesBroughtForwardError(boxRetriever)
    )
  }

  private def lossesBroughtForwardError(retriever: ComputationsBoxRetriever) = {
    failIf(this.orZero != Math.min(retriever.cp117().value, retriever.cp281c().orZero)) {
      Set(CtValidation(None, "error.lossesBroughtForwardError.error1"))
    }
  }
}


object CP283c {

  def apply(int: Int): CP283c = CP283c(Some(int))
}