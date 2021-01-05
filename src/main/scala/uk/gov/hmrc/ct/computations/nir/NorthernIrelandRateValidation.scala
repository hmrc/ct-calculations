/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.nir

import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever
import uk.gov.hmrc.ct.computations.losses._

trait NorthernIrelandRateValidation {

  def mayHaveNirLosses(computationsBoxRetriever: ComputationsBoxRetriever): Boolean = {
    nirActiveForCurrentAccountingPeriod(computationsBoxRetriever) || computationsBoxRetriever.cpQ117().orFalse
  }

  def nirActiveForCurrentAccountingPeriod(computationsBoxRetriever: ComputationsBoxRetriever): Boolean = computationsBoxRetriever match {
    case retriever: ComputationsBoxRetriever with AboutThisReturnBoxRetriever => northernIrelandJourneyActive(retriever)
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
    retriever.cp281c().orZero <= retriever.cp281b().orZero
  }

  def excessNorthernIrelandTradingProfit(boxRetriever: ComputationsBoxRetriever): Boolean = {
    import boxRetriever._

    cp117().value > cp281c().orZero
  }

  def moreLossThanTradingProfit(boxRetriever: ComputationsBoxRetriever): Boolean = {
    import boxRetriever._

    cp117().value < cp281c().orZero
  }


}
