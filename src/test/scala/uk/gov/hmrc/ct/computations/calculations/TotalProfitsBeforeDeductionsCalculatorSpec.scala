

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._

class TotalProfitsBeforeDeductionsCalculatorSpec extends WordSpec with Matchers {

  "NetTradingProfitCalculator" should {

    "return correct value when consider CP284 as zero when CP284 is -ve" in new TotalProfitsBeforeDeductionsCalculator {
      val result = computeTotalProfitsBeforeDeductionsAndReliefs(cp284 = CP284(Some(-10000)),
        cp58 = CP58(100),
        cp511 = CP511(50),
        cp502 = CP502(Some(10)))
      result shouldBe CP293(160)
    }

    "return correct value when all fields have values and trading profit is greater than zero" in new TotalProfitsBeforeDeductionsCalculator {
      val result = computeTotalProfitsBeforeDeductionsAndReliefs(cp284 = CP284(Some(1000)),
        cp58 = CP58(100),
        cp511 = CP511(50),
        cp502 = CP502(Some(10)))
      result shouldBe CP293(1160)
    }

    "return correct value when trading profit is not populated and defaulted to zero" in new TotalProfitsBeforeDeductionsCalculator {
      val result = computeTotalProfitsBeforeDeductionsAndReliefs(cp284 = CP284(None),
        cp58 = CP58(100),
        cp511 = CP511(50),
        cp502 = CP502(Some(10)))
      result shouldBe CP293(160)
    }

    "return correct value when all fields have values and trading profit is greater than zero and ancillary is not populated" in new TotalProfitsBeforeDeductionsCalculator {
      val result = computeTotalProfitsBeforeDeductionsAndReliefs(cp284 = CP284(Some(1000)),
        cp58 = CP58(100),
        cp511 = CP511(50),
        cp502 = CP502(None))
      result shouldBe CP293(1150)
    }
  }
}
