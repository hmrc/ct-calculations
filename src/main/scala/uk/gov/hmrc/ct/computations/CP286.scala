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
import uk.gov.hmrc.ct.box.validation.OptionalMoneyValidation
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.calculations.TradingLossesCP286MaximumCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP286(value: Option[Int]) extends CtBoxIdentifier(name = "Losses claimed from a later AP")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation
  with OptionalMoneyValidation
  with TradingLossesCP286MaximumCalculator {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    validateMoneyRange("CP286", min = 0, max = calculateMaximumCP286(retrieveCP117(), retrieveCATO01(), retrieveCP998(), retrieveCP281())) ++
    (
      boxRetriever.retrieveCPQ18().value match {
        case Some(false) if value.nonEmpty =>
                  Set(CtValidation(Some("CP286"), "error.CP286.cannot.exist"))
        case Some(true) if value.isEmpty =>
                  Set(CtValidation(Some("CP286"), "error.CP286.required"))
        case _ => Set.empty
      }
    )
  }
}
