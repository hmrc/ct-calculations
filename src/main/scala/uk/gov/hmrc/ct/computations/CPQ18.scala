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

case class CPQ18(value: Option[Boolean]) extends CtBoxIdentifier(name = "Claim any trading losses carried back from a later period against profits in this period.")
                                         with CtOptionalBoolean
                                         with Input
                                         with ValidatableBox[ComputationsBoxRetriever]
                                         with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    if (value.isEmpty)
      validateWhenEmpty(boxRetriever)
    else
      validateWhenPopulated(boxRetriever)
  }

  private def validateWhenEmpty(br: ComputationsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      requiredIf(answeredNoToTradingLossesNotUsedFromPreviousPeriod(br)),
      requiredIf(answeredNoToCurrentTradingLossesAgainstNonTradingProfit(br)),
      requiredIf(And(notAnsweredTradingLossesNotUsedFromPreviousPeriod(br),
                        notAnsweredCurrentTradingLossesAgainstNonTradingProfit(br),
                        noTradingLoss(br), noTradingProfit(br), hasNonTradingProfit(br)))
    )
  }

  private def validateWhenPopulated(br: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      cannotExistIf(And(answeredYesToTradingLossesNotUsedFromPreviousPeriod(br), noTradingLoss(br),
                           noNonTradingProfit(br), netTradingProfitEqualsTradingProfit(br))),
      cannotExistIf(And(answeredYesToCurrentTradingLossesAgainstNonTradingProfit(br),
                           answeredYesToCurrentTradingLossesAgainstToPreviousPeriod(br), hasTradingLoss(br),
                           hasNonTradingProfit(br))),
      cannotExistIf(And(answeredYesToTradingLossesNotUsedFromPreviousPeriod(br), noTradingLoss(br),
                           Or(noTradingProfit(br), netTradingProfitPlusNonTradingProfitEqualsZero(br)))),
      cannotExistIf(And(answeredYesToCurrentTradingLossesAgainstNonTradingProfit(br), noTradingLoss(br),
                           nonTradingProfitNotGreaterThanTradingLoss(br))),
      cannotExistIf(And(notAnsweredCurrentTradingLossesAgainstNonTradingProfit(br),
                           notAnsweredTradingLossesNotUsedFromPreviousPeriod(br), noNonTradingProfit(br)))
    )
  }
}
