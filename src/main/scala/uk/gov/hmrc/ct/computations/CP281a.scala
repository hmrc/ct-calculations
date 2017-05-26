/*
 * Copyright 2017 HM Revenue & Customs
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

case class CP281a(value: Option[Int]) extends CtBoxIdentifier("Losses brought forward from before 01/04/2017")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(retriever.cpQ17().isTrue && retriever.cp1().value.isAfter(CPQ17.lossReform2017) && !hasValue),
      cannotExistErrorIf(hasValue && (retriever.cpQ17().isFalse || !retriever.cp1().value.isAfter(CPQ17.lossReform2017))),
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
