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

case class CP281c(value: Option[Int])
  extends CtBoxIdentifier("NIR Losses brought forward from on or after 01/04/2017")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with NorthernIrelandRateValidation {

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import uk.gov.hmrc.ct.computations.losses._
    collectErrors(
      requiredErrorIf(retriever.cpQ117().isTrue &&
                      lossReform2017Applies(retriever.cp2()) &&
                      mayHaveNirLosses(retriever) &&
                      !hasValue)(),
      cannotExistErrorIf(hasValue &&
                        (retriever.cpQ117().isFalse ||
                          !lossReform2017Applies(retriever.cp2()) ||
                          !mayHaveNirLosses(retriever))
      )(),
      sumOfLossesBroughtForwardError(retriever),
      passIf(lossesBroughtForwardTotalsCorrect(retriever)){
        Set(CtValidation(Some("CP281c"), "error." + "CP281b.breakdown.sum.incorrect"))
      } ()
    )

  }

  private def sumOfLossesBroughtForwardError(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cpQ117().isTrue && this.orZero + retriever.cp281d().orZero != retriever.cp281b().orZero){
      Set(CtValidation(None,"error.lossesBroughtForward.incorrect"))
    } ()
  }
}

object CP281c {

  def apply(int: Int): CP281c = new CP281c(Some(int))
}