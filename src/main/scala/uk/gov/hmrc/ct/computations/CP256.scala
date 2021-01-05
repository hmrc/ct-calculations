/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.AdjustedTradingProfitCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP256(value: Int) extends CtBoxIdentifier(name = "Adjusted Trading Or Loss") with CtInteger

object CP256 extends Calculated[CP256, ComputationsBoxRetriever] with AdjustedTradingProfitCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP256 = {
    adjustedTradingProfitCalculationNonOptional(cp117 = fieldValueRetriever.cp117())
  }
}
