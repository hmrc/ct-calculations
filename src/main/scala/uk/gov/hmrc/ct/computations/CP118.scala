/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, MustBeZeroOrPositive}
import uk.gov.hmrc.ct.computations.calculations.AdjustedTradingProfitOrLossCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class
CP118(value: Int) extends CtBoxIdentifier(name = "Adjusted Trading Loss") with CtInteger with MustBeZeroOrPositive

object CP118 extends Calculated[CP118, ComputationsBoxRetriever] with AdjustedTradingProfitOrLossCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP118 = {
    calculateAdjustedTradingLoss(
      cp44 = fieldValueRetriever.cp44(),
      cp54 = fieldValueRetriever.cp54(),
      cp59 = fieldValueRetriever.cp59(),
      cp186 = fieldValueRetriever.cp186(),
      cp91 = fieldValueRetriever.cp91(),
      cp670 = fieldValueRetriever.cp670(),
      cp668 = fieldValueRetriever.cp668(),
      cp297 = fieldValueRetriever.cp297(),
      cpq19 = fieldValueRetriever.cpQ19()
    )
  }
}
