package uk.gov.hmrc.ct.ct600e.v3.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600e.v3._

class IncomeCalculatorSpec extends WordSpec with Matchers with IncomeCalculator {

  "IncomeCalculator" should {
    "return None if all income boxes are None" in {
      calculateTotalIncome(e50 = E50(None), e55 = E55(None), e60 = E60(None), e65 = E65(None), e70 = E70(None), e75 = E75(None), e80 = E80(None), e85 = E85(None)) shouldBe E90(None)
    }
  }
}
