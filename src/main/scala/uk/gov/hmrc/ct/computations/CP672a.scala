/*
 * Copyright 2023 HM Revenue & Customs
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
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class CP672a(value: Option[Int]) extends CtBoxIdentifier(name = "Out Of Proceeds from disposals from main pool")  with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {

  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    val hasCompanyCeasedTrading = boxRetriever.cpQ8().isTrue

    val max = {
      if (hasCompanyCeasedTrading) {
        val proceedsFromDisposals = boxRetriever.cp84().orZero

        exceedsMax(value, proceedsFromDisposals, "CP84.exceeds.max")
      }
      else {
        val proceedsFromDisposalsFromMainPool = boxRetriever.cp672().orZero

        exceedsMax(value, proceedsFromDisposalsFromMainPool, "CP672.exceeds.max")
      }
    }

    collectErrors(
      validateZeroOrPositiveInteger(),
      max
    )

  }
}
object CP672a {

  def apply(value: Int): CP672a = CP672a(Some(value))
}


