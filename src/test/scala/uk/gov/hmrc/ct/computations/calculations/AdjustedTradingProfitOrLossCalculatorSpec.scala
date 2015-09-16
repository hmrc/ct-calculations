package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._

class AdjustedTradingProfitOrLossCalculatorSpec extends WordSpec with Matchers {

  "Calculator for Adjusted Trading Profit (CP117)" should {
    "return a trading profit calculated as CP44 + CP54 - CP59 - CP186 + CP91" in new AdjustedTradingProfitOrLossCalculator {
      val cp117 = calculateAdjustedTradingProfit(
        cp44 = CP44(5000),
        cp54 = CP54(1000),
        cp59 = CP59(250),
        cp186 = CP186(Some(500)),
        cp91 = CP91(Some(1000))
      )
      cp117 shouldBe CP117(6250)
    }
    "return a trading profit of zero if there is a loss CP44 + CP54 - CP59 - CP186 + CP91" in new AdjustedTradingProfitOrLossCalculator {
      val cp117 = calculateAdjustedTradingProfit(
        cp44 = CP44(-40000),
        cp54 = CP54(1000),
        cp59 = CP59(250),
        cp186 = CP186(Some(500)),
        cp91 = CP91(Some(1000))
      )
      cp117 shouldBe CP117(0)
    }
  }

  "Calculator for Adjusted Trading loss (CP118)" should {
    "return a trading loss of zero if there is a trading profit > 0 calculated as CP44 + CP54 - CP59 - CP186 + CP91" in new AdjustedTradingProfitOrLossCalculator {
      val cp118 = calculateAdjustedTradingLoss(cp44 = CP44(5000),
                                              cp54 = CP54(1000),
                                              cp59 = CP59(250),
                                              cp186 = CP186(Some(500)),
                                              cp91 = CP91(Some(1000)))
      cp118 shouldBe CP118(0)
    }
    "return a trading loss if there is a loss CP44 + CP54 - CP59 - CP186 + CP91" in new AdjustedTradingProfitOrLossCalculator {
      val cp118 = calculateAdjustedTradingLoss(cp44 = CP44(-40000),
                                              cp54 = CP54(1000),
                                              cp59 = CP59(250),
                                              cp186 = CP186(Some(500)),
                                              cp91 = CP91(Some(1000)))
      cp118 shouldBe CP118(38750)
    }
  }
}

