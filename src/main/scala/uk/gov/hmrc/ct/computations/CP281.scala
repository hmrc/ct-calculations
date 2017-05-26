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

case class CP281(value: Option[Int]) extends CtBoxIdentifier("Losses brought forward")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      requiredErrorIf(value.isEmpty && boxRetriever.cpQ17.isTrue),
      cannotExistErrorIf(value.nonEmpty && !boxRetriever.cpQ17().orFalse),
      exceedsMax(value),
      belowMin(value, min = 1),
      sumBreakdownIncorrectErrors(boxRetriever)
    )
  }

  private def sumBreakdownIncorrectErrors(retriever: ComputationsBoxRetriever) = {
    val cp281a = retriever.cp281a()
    val cp281b = retriever.cp281b()
    failIf((cp281a.value.isDefined || cp281b.value.isDefined) && cp281a + cp281b != this.orZero) {
      Set(CtValidation(None, "error.CP281.breakdown.sum.incorrect"))
    }
  }
}

object CP281 {

  def apply(int: Int): CP281 = CP281(Some(int))
}
