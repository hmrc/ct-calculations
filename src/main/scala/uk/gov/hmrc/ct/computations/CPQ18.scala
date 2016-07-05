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

  val checkRequired = requiredIf("CPQ18") _
  val checkCannotExist = cannotExistIf("CPQ18") _

  private def validateWhenEmpty(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {

    Set(
      checkRequired(answeredNoToTradingLossesNotUsedFromPreviousPeriod),
      checkRequired(answeredNoToCurrentTradingLossesAgainstNonTradingProfit),
      checkRequired(And(notAnsweredTradingLossesNotUsedFromPreviousPeriod,
                        notAnsweredCurrentTradingLossesAgainstNonTradingProfit,
                        noTradingLoss, noTradingProfit, hasNonTradingProfit))
    ).flatMap { predicate =>
      predicate(boxRetriever)
    }
  }

  private def validateWhenPopulated(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    Set(
      checkCannotExist(And(answeredYesToTradingLossesNotUsedFromPreviousPeriod, noTradingLoss,
                           noNonTradingProfit, netTradingProfitEqualsTradingProfit)),
      checkCannotExist(And(answeredYesToCurrentTradingLossesAgainstNonTradingProfit,
                           answeredYesToCurrentTradingLossesAgainstToPreviousPeriod, hasTradingLoss,
                           hasNonTradingProfit)),
      checkCannotExist(And(answeredYesToTradingLossesNotUsedFromPreviousPeriod, noTradingLoss,
                           Or(noTradingProfit, netTradingProfitPlusNonTradingProfitEqualsZero))),
      checkCannotExist(And(answeredYesToCurrentTradingLossesAgainstNonTradingProfit, noTradingLoss,
                           nonTradingProfitNotGreaterThanTradingLoss)),
      checkCannotExist(And(notAnsweredCurrentTradingLossesAgainstNonTradingProfit,
                           notAnsweredTradingLossesNotUsedFromPreviousPeriod, noNonTradingProfit))
    ).flatMap { predicate =>
      predicate(boxRetriever)
    }
  }
}
