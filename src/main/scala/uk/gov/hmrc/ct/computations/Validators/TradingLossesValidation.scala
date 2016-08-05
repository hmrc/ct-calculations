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

import uk.gov.hmrc.ct.box.{CtValidation, Validators}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._

trait TradingLossesValidation extends Validators[ComputationsBoxRetriever] {

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
    retriever: ComputationsBoxRetriever => retriever.cpQ17().value == Some(false)
  }

  protected val answeredYesToTradingLossesNotUsedFromPreviousPeriod = {
    retriever: ComputationsBoxRetriever => retriever.cpQ17().value == Some(true)
  }

  protected val notAnsweredTradingLossesNotUsedFromPreviousPeriod = {
    retriever: ComputationsBoxRetriever => retriever.cpQ17().value.isEmpty
  }

  protected val answeredNoToCurrentTradingLossesAgainstNonTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.cpQ19().value == Some(false)
  }

  protected val answeredYesToCurrentTradingLossesAgainstNonTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.cpQ19().value == Some(true)
  }

  protected val answeredYesToCurrentTradingLossesAgainstToPreviousPeriod = {
    retriever: ComputationsBoxRetriever => retriever.cpQ20().value == Some(true)
  }

  protected val notAnsweredCurrentTradingLossesAgainstNonTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.cpQ19().value.isEmpty
  }

  protected val netTradingProfitGreaterThanTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.cp284().orZero > retriever.cp117().value
  }

  protected val netTradingProfitEqualsTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.cp284().orZero == retriever.cp117().value
  }

  protected val netTradingProfitPlusNonTradingProfitGreaterThanZero = {
    retriever: ComputationsBoxRetriever => retriever.cp284().orZero + retriever.cato01().value > 0
  }

  protected val nonTradingProfitNotGreaterThanTradingLoss = {
    retriever: ComputationsBoxRetriever => retriever.cp118().value <= retriever.cato01().value
  }

  protected val netTradingProfitPlusNonTradingProfitEqualsZero = {
    retriever: ComputationsBoxRetriever => retriever.cp284().orZero + retriever.cato01().value == 0
  }

  protected val hasTradingLoss = {
    retriever: ComputationsBoxRetriever => retriever.cp118().value > 0
  }

  protected val hasTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.cp117().value > 0
  }

  protected val noTradingLoss = {
    retriever: ComputationsBoxRetriever => retriever.cp118().value == 0
  }

  protected val noTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.cp117().value == 0
  }

  protected val noNonTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.cato01().value == 0
  }

  protected val hasNonTradingProfit = {
    retriever: ComputationsBoxRetriever => retriever.cato01().value > 0
  }

  protected val noTradingProfitOrLoss = {
    And(noTradingProfit, noTradingLoss) _
  }
}
