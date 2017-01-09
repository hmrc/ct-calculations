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

case class CP287(value: Option[Int]) extends CtBoxIdentifier(name = "Amount of loss carried back to earlier periods")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {


  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    val max = cp118().value - cp998().orZero

    collectErrors(
      requiredIf(value.isEmpty && boxRetriever.cpQ20.isTrue),
      cannotExistIf({ value.nonEmpty && !boxRetriever.cpQ20().orFalse }),
      exceedsMax(value, max),
      belowMin(value, 1)
    )
  }
}

object CP287 {
  def apply(int: Int): CP287 = CP287(Some(int))
}
