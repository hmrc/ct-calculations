/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TradingLossesThisAndLaterPeriodCalculation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP294(value: Int) extends CtBoxIdentifier(name = "Trading losses of this period and later periods") with CtInteger

object CP294 extends Calculated[CP294, ComputationsBoxRetriever] with TradingLossesThisAndLaterPeriodCalculation {
  override def calculate(boxValueRetriever: ComputationsBoxRetriever): CP294 = {
    import boxValueRetriever._
    tradingLosses(cp286(),
                  cp998())
  }
}
