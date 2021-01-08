/*
 * Copyright 2021 HM Revenue & Customs
 *
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
      requiredErrorIf(answeredNoToTradingLossesNotUsedFromPreviousPeriod(br)),
      requiredErrorIf(answeredNoToCurrentTradingLossesAgainstNonTradingProfit(br)),
      requiredErrorIf(And(notAnsweredTradingLossesNotUsedFromPreviousPeriod(br),
                        notAnsweredCurrentTradingLossesAgainstNonTradingProfit(br),
                        noTradingLoss(br), noTradingProfit(br), hasNonTradingProfit(br)))
    )
  }

  private def validateWhenPopulated(br: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      cannotExistErrorIf(And(answeredYesToTradingLossesNotUsedFromPreviousPeriod(br), noTradingLoss(br),
                           noNonTradingProfit(br), netTradingProfitEqualsTradingProfit(br))),
      cannotExistErrorIf(And(answeredYesToCurrentTradingLossesAgainstNonTradingProfit(br),
                           answeredYesToCurrentTradingLossesAgainstToPreviousPeriod(br), hasTradingLoss(br),
                           hasNonTradingProfit(br))),
      cannotExistErrorIf(And(answeredYesToTradingLossesNotUsedFromPreviousPeriod(br), noTradingLoss(br),
                           Or(noTradingProfit(br), netTradingProfitPlusNonTradingProfitEqualsZero(br)))),
      cannotExistErrorIf(And(answeredYesToCurrentTradingLossesAgainstNonTradingProfit(br), noTradingLoss(br),
                           nonTradingProfitNotGreaterThanTradingLoss(br))),
      cannotExistErrorIf(And(notAnsweredCurrentTradingLossesAgainstNonTradingProfit(br),
                           notAnsweredTradingLossesNotUsedFromPreviousPeriod(br), noNonTradingProfit(br)))
    )
  }
}
