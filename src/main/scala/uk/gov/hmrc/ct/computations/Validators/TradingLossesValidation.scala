package uk.gov.hmrc.ct.computations.Validators

import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait TradingLossesValidation {

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

  protected def cannotExistIf(boxId: String)(predicate: (ComputationsBoxRetriever) => Boolean)(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    if (predicate(boxRetriever))
      Set(CtValidation(Some(boxId), s"error.$boxId.cannot.exist"))
    else
      Set.empty
  }
}
