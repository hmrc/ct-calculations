package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._

class TotalDeductionsCalculatorSpec extends WordSpec with Matchers {

  "Total Deductions Calculator" should {
    "calculate deductions with populated values" in new TotalDeductionsCalculator {
      totalDeductionsCalculation(cp55 = CP55(Some(1)),
                                 cp57 = CP57(Some(2)),
                                 cp58 = CP58(3),
                                 cp504 = CP504(4),
                                 cp505 = CP505(Some(5))) shouldBe CP59(15)
    }
  }
}
