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
import uk.gov.hmrc.ct.computations.calculations.TradingLossesCP286MaximumCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP286(value: Option[Int]) extends CtBoxIdentifier(name = "Losses claimed from a later AP")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation
  with TradingLossesCP286MaximumCalculator {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val box = "CP286"
    collectErrors(
      Set(
        requiredIf(box) { boxRetriever: ComputationsBoxRetriever => value.isEmpty && boxRetriever.retrieveCPQ18().value == Some(true) },
        cannotExistIf(box) { boxRetriever: ComputationsBoxRetriever => value.nonEmpty && !boxRetriever.retrieveCPQ18().orFalse },
        exceedsMax(box)(value, calculateMaximumCP286(boxRetriever.retrieveCP117(), boxRetriever.retrieveCATO01(), boxRetriever.retrieveCP998(), boxRetriever.retrieveCP281())),
        belowMin(box)(value, 0)
      )
    )(boxRetriever)
  }
}
