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

case class CPQ19(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you wish to claim your trading losses for this period against your profits for this period?")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val cpq19Error: Set[CtValidation] = (retrieveCPQ17().value, value) match {
      case (Some(_), Some(_)) => Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist"))
      case _ => Set.empty
    }

    val valueEmpty = {
      retriever: ComputationsBoxRetriever => value.isEmpty
    }

    val valuePopulated = {
      retriever: ComputationsBoxRetriever => value.nonEmpty
    }

    val errors = Set(
      requiredIf("CPQ19")(And(hasTradingLoss, hasNonTradingProfit, valueEmpty)) _,
      cannotExistIf("CPQ19")(And(hasNonTradingProfit, noTradingLoss, valuePopulated)) _,
      cannotExistIf("CPQ19")(And(noNonTradingProfit, hasTradingLoss, valuePopulated)) _,
      cannotExistIf("CPQ19")(And(noNonTradingProfit, noTradingLoss, valuePopulated)) _
    ).flatMap { p =>
      p(boxRetriever)
    }

    errors ++ cpq19Error
  }
}
