/*
 * Copyright 2021 HM Revenue & Customs
 *
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

    val valueEmpty = () => value.isEmpty
    val valuePopulated = () => value.nonEmpty

    collectErrors(
      requiredErrorIf(And(hasTradingLoss(boxRetriever), hasNonTradingProfit(boxRetriever), valueEmpty)),
      cannotExistErrorIf(And(hasNonTradingProfit(boxRetriever), noTradingLoss(boxRetriever), valuePopulated)) ,
      cannotExistErrorIf(And(noNonTradingProfit(boxRetriever), valuePopulated))
    )

  }
}
