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

package uk.gov.hmrc.ct.computations.Validators

import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._

trait TradingLossesValidation {

  protected val boxId = getClass.getSimpleName

  protected def allLossesOffsetByNonTradingProfit(cp118: Int, cato01: Int): Boolean = {
    cp118 <= cato01
  }

  protected def noLossesWithNonTradingProfit(cp118: Int, cato01: Int): Boolean = {
    cp118 == 0 && cato01 >= 0
  }

  protected def moreLossesThanNonTradeProfit(cp118: Int, cato01: Int): Boolean = {
    cp118 > cato01
  }

  protected val answeredNoToTradingLossesNotUsedFromPreviousPeriod = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCPQ17().value == Some(false)
  }

  protected val answeredYesToTradingLossesNotUsedFromPreviousPeriod = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCPQ17().value == Some(true)
  }

  protected val notAnsweredTradingLossesNotUsedFromPreviousPeriod = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCPQ17().value.isEmpty
  }

  protected val answeredNoToCurrentTradingLossesAgainstNonTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCPQ19().value == Some(false)
  }

  protected val answeredYesToCurrentTradingLossesAgainstNonTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCPQ19().value == Some(true)
  }

  protected val notAnsweredCurrentTradingLossesAgainstNonTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCPQ19().value.isEmpty
  }

  protected val netTradingProfitGreaterThanTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCP284().orZero > retriever.retrieveCP117().value
  }

  protected val netTradingProfitEqualsTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCP284().orZero == retriever.retrieveCP117().value
  }

  protected val netTradingProfitPlusNonTradingProfitGreaterThanZero = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCP284().orZero + retriever.retrieveCATO01().value > 0
  }

  protected val nonTradingProfitNotGreaterThanTradingLoss = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCP118().value <= retriever.retrieveCATO01().value
  }

  protected val netTradingProfitPlusNonTradingProfitEqualsZero = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCP284().orZero + retriever.retrieveCATO01().value == 0
  }

  protected val hasTradingLoss = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCP118().value > 0
  }

  protected val hasTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCP117().value > 0
  }

  protected val noTradingLoss = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCP118().value == 0
  }

  protected val noTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCP117().value == 0
  }

  protected val noNonTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCATO01().value == 0
  }

  protected val hasNonTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.retrieveCATO01().value > 0
  }

  protected val noTradingProfitOrLoss = {
    And(noTradingProfit, noTradingLoss) _
  }

  protected def And(predicates: ((ComputationsBoxRetriever) => Boolean)*)(boxRetriever: ComputationsBoxRetriever): Boolean = {
    !predicates.exists { p => !p(boxRetriever)}
  }

  protected def Or(predicates: ((ComputationsBoxRetriever) => Boolean)*)(boxRetriever: ComputationsBoxRetriever): Boolean = {
    predicates.exists { p => p(boxRetriever)}
  }

  protected def requiredIf(boxId: String)(predicate: (ComputationsBoxRetriever) => Boolean)(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    if (predicate(boxRetriever))
      Set(CtValidation(Some(boxId), s"error.$boxId.required"))
    else
      Set.empty
  }

  protected def exceedsMax(boxId: String)(value: Option[Int], max: Int = MAX_MONEY_AMOUNT_ALLOWED)(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    value match {
      case (Some(v)) if v > max => Set(CtValidation(Some(boxId), s"error.$boxId.exceeds.max"))
      case _ => Set.empty
    }
  }

  protected def belowMin(boxId: String)(value: Option[Int], min: Int = MIN_MONEY_AMOUNT_ALLOWED)(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    value match {
      case (Some(v)) if v < min => Set(CtValidation(Some(boxId), s"error.$boxId.below.min"))
      case _ => Set.empty
    }
  }

  protected def cannotExistIf(boxId: String)(predicate: (ComputationsBoxRetriever) => Boolean)(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    if (predicate(boxRetriever))
      Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
    else
      Set.empty
  }

  protected def collectErrors(predicates: Set[(ComputationsBoxRetriever) => Set[CtValidation]])(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    predicates.flatMap { predicate =>
      predicate(boxRetriever)
    }
  }
}
