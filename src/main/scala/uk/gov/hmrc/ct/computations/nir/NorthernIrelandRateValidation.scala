/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.nir

import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever

trait NorthernIrelandRateValidation {

  def mayHaveNirLosses(computationsBoxRetriever: ComputationsBoxRetriever): Boolean = {
    nirActiveForCurrentAccountingPeriod(computationsBoxRetriever) || computationsBoxRetriever.cpQ117().orFalse
  }

  def nirActiveForCurrentAccountingPeriod(computationsBoxRetriever: ComputationsBoxRetriever): Boolean = computationsBoxRetriever match {
    case retriever: ComputationsBoxRetriever with AboutThisReturnBoxRetriever => retriever.b5().orFalse
    case _ => false
  }

  def nirLossesAllOffsetAgainstNITradingProfit(boxRetriever: ComputationsBoxRetriever): Boolean = {
    import boxRetriever._

    cp283c().orZero == cp281c().orZero
  }

  def nirLossesNotAllOffsetAgainstNITradingProfit(boxRetriever: ComputationsBoxRetriever): Boolean = {
    import boxRetriever._

    cp283c().orZero < cp281c().orZero
  }

  def mainStreamLossesNotAllOffsetAgainstNonTradingProfit(boxRetriever: ComputationsBoxRetriever): Boolean = {
    import boxRetriever._

    cp997d().orZero < cp281d().orZero
  }

  def mainStreamLossesAllOffsetAgainstNonTradingProfit(boxRetriever: ComputationsBoxRetriever): Boolean = {
    import boxRetriever._

    cp997d().orZero == cp281d().orZero
  }

  def nirLossesCanBeOffsetAgainstNonTradingProfit(boxRetriever: ComputationsBoxRetriever): Boolean = {
    true
  }

  def mainStreamLossesCanBeOffsetAgainstNirTradingProfit(boxRetriever: ComputationsBoxRetriever): Boolean = {
    true
  }

  def lossesBroughtForwardTotalsCorrect(retriever: ComputationsBoxRetriever): Boolean = {
    retriever.cp281c().orZero >= retriever.cp281b().orZero
  }

//  private def whenNorthernIrelandRateActive(boxRetriever: ComputationsBoxRetriever)( predicates: (ComputationsBoxRetriever => Boolean)* ): Boolean = {
//    nirActiveForCurrentAccountingPeriod(boxRetriever) && predicates.fold(true)((incoming, pred) => incoming && pred(boxRetriever))
//  }
}
