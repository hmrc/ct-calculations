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

case class CPQ19(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you wish to claim your trading losses for this period against your profits for this period?")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation
  with Validators {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    val valueEmpty = () => value.isEmpty
    val valuePopulated = () => value.nonEmpty

    collectErrors(
      requiredIf(And(hasTradingLoss(boxRetriever), hasNonTradingProfit(boxRetriever), valueEmpty)),
      cannotExistIf(And(hasNonTradingProfit(boxRetriever), noTradingLoss(boxRetriever), valuePopulated)) ,
      cannotExistIf(And(noNonTradingProfit(boxRetriever), hasTradingLoss(boxRetriever), valuePopulated)) ,
      cannotExistIf(And(noNonTradingProfit(boxRetriever), noTradingLoss(boxRetriever), valuePopulated)) ,
      (cpQ17().value, value) match {
        case (Some(_), Some(_)) => Set(CtValidation(Some(boxId), "error.CPQ19.cannot.exist.cpq17"))
        case _ => Set.empty[CtValidation]
      }
    )

  }
}
