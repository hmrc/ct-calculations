package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP43, CP502, CP509, CP510}

trait NonTradeIncomeCalculator extends CtTypeConverters {

  def nonTradeIncomeCalculation(cp43: CP43,
                                cp502: CP502,
                                cp509: CP509,
                                cp510: CP510): CATO01 = {
    CATO01(cp43 + cp502 + cp509 + cp510)
  }
}
