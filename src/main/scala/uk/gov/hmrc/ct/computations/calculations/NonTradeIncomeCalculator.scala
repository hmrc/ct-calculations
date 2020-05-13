

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.{CATO01, CATO23}
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait NonTradeIncomeCalculator extends CtTypeConverters {

  def nonTradeIncomeCalculation(cp43: CP43,
                                cp502: CP502,
                                cp509: CP509,
                                cp510: CP510): CATO01 = {
    CATO01(cp43 + cp502 + cp509 + cp510)
  }

  def NetNonTradeIncomeCalculation(cato01: CATO01, cp997NI: CP997NI, cp998: CP998): CATO23 ={
    CATO23(cato01 - cp997NI - cp998.orZero)

  }
}
