/*
 * Copyright 2024 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.{CtTypeConverters, CtValidation, Validators}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait TradingLossesValidation extends Validators with CtTypeConverters {
  
   def allLossesOffsetByNonTradingProfit(cp118: Int, cato01: Int): Boolean = {
    cp118 <= cato01
  }

   def noLossesWithNonTradingProfit(cp118: Int, cato01: Int): Boolean = {
    cp118 == 0 && cato01 >= 0
  }

   def moreLossesThanNonTradeProfit(cp118: Int, cato01: Int): Boolean = {
    cp118 > cato01
  }

   def answeredNoToTradingLossesNotUsedFromPreviousPeriod(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cpQ17().hasValue && boxRetriever.cpQ17().isFalse
  }

   def answeredYesToTradingLossesNotUsedFromPreviousPeriod(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cpQ17().isTrue
  }

   def notAnsweredTradingLossesNotUsedFromPreviousPeriod(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cpQ17().noValue
  }

   def answeredNoToCurrentTradingLossesAgainstNonTradingProfit(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cpQ19().hasValue && boxRetriever.cpQ19().isFalse
  }

   def answeredYesToCurrentTradingLossesAgainstNonTradingProfit(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cpQ19().isTrue
  }

   def answeredYesToCurrentTradingLossesAgainstToPreviousPeriod(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cpQ20().isTrue
  }

   def notAnsweredCurrentTradingLossesAgainstNonTradingProfit(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cpQ19().noValue
  }

   def netTradingProfitGreaterThanTradingProfit(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
    boxRetriever.cp284().orZero > boxRetriever.cp117().value
  }

   def netTradingProfitEqualsTradingProfit(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cp284().orZero == boxRetriever.cp117().value
  }

   def netTradingProfitPlusNonTradingProfitGreaterThanZero(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cp284().orZero + boxRetriever.cato01().value > 0
  }

   def nonTradingProfitNotGreaterThanTradingLoss(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cp118().value <= boxRetriever.cato01().value
  }

   def netTradingProfitPlusNonTradingProfitEqualsZero(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cp284().orZero + boxRetriever.cato01().value == 0
  }

  def hasTradingLoss(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
    boxRetriever.cp118().value > 0
  }

   def hasTradingProfit(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cp117().value > 0
  }

   def noTradingLoss(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cp118().value == 0
  }

   def noTradingProfit(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cp117().value == 0
  }

   def noNonTradingProfit(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cato01().value == 0
  }

   def hasNonTradingProfit(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
     boxRetriever.cato01().value > 0
  }

   def noTradingProfitOrLoss(boxRetriever: ComputationsBoxRetriever)(): Boolean = {
    And(noTradingProfit(boxRetriever), noTradingLoss(boxRetriever))()
  }

  def sumOfBroughtForwardErrors(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    failIf(retriever.cp283a() + retriever.cp283b() > retriever.cp117()) {
      Set(CtValidation(Some("CP283a"), "error.CP283.exceeds.totalProfit"),
        CtValidation(Some("CP283b"), "error.CP283.exceeds.totalProfit")
      )
    } ()
  }

}
