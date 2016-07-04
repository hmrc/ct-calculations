/*
 * Copyright 2016 HM Revenue & Customs
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
    val max = retrieveCP118().value - retrieveCP998().orZero

    collectErrors(Set(
      requiredIf("CP287") { boxRetriever: ComputationsBoxRetriever => value.isEmpty && boxRetriever.retrieveCPQ20().value == Some(true) },
      cannotExistIf("CP287") { boxRetriever: ComputationsBoxRetriever => value.nonEmpty && !boxRetriever.retrieveCPQ20().orFalse },
      exceedsMax("CP287")(value, max),
      belowMin("CP287")(value, 1)
    ))(boxRetriever)
  }
}

object CP287 {

  def apply(int: Int): CP287 = CP287(Some(int))
}
