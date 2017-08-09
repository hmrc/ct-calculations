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
import uk.gov.hmrc.ct.computations.calculations.TradingLossesCP286MaximumCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP286(value: Option[Int]) extends CtBoxIdentifier(name = "Losses claimed from a later AP")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation
  with TradingLossesCP286MaximumCalculator {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
        requiredErrorIf(value.isEmpty && boxRetriever.cpQ18.isTrue),
        cannotExistErrorIf(value.nonEmpty && !boxRetriever.cpQ18().orFalse),
        exceedsMax(value, calculateMaximumCP286(boxRetriever.cp117(), boxRetriever.cato01(), boxRetriever.cp283(), boxRetriever.cp997(), boxRetriever.cp998())),
        belowMin(value, 0)
    )
  }
}
