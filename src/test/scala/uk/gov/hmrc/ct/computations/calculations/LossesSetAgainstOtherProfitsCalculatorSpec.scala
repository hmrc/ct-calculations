

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.computations.{CP118, CP997, CP998, CPQ19}

class LossesSetAgainstOtherProfitsCalculatorSpec extends WordSpec with Matchers {

  "Losses Set Against Other Profits Calculator" should {
    "return CP118 when CP118 is less than CATO01 and CP118 is positive" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp997 = CP997(None), cp118 = CP118(9), cpq19 = CPQ19(Some(true))) shouldBe CP998(Some(9))
    }
    "return CATO01 when CATO01 is less CP118, CP997 is None and CP118 is positive" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp997 = CP997(None), cp118 = CP118(19), cpq19 = CPQ19(Some(true))) shouldBe CP998(Some(10))
    }
    "return CATO01 when CATO01 is less CP118, CP997 is 0 and CP118 is positive" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp997 = CP997(Some(0)), cp118 = CP118(19), cpq19 = CPQ19(Some(true))) shouldBe CP998(Some(10))
    }
    "return CATO01 minus CP997 when CATO01 is less CP118, CP997 is positive and CP118 is positive" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp997 = CP997(Some(1)), cp118 = CP118(19), cpq19 = CPQ19(Some(true))) shouldBe CP998(Some(9))
    }
    "return CATO01 when CATO01 equals absolute CP118 and CP118 is positive" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp997 = CP997(None), cp118 = CP118(10), cpq19 = CPQ19(Some(true))) shouldBe CP998(Some(10))
    }
    "return None when CPQ19 is None" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp997 = CP997(None), cp118 = CP118(10), cpq19 = CPQ19(None)) shouldBe CP998(None)
    }
    "return None when CPQ19 is false" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp997 = CP997(None), cp118 = CP118(10), cpq19 = CPQ19(Some(false))) shouldBe CP998(None)
    }
  }
}
