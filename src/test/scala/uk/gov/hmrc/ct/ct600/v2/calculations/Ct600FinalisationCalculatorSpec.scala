/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.ct600.v2.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600._
import uk.gov.hmrc.ct.ct600.v2._

class Ct600FinalisationCalculatorSpec extends WordSpec with Matchers {

  "Ct600FinalisationCalculator - computeTaxPayable" should {

    "return taxPayable when incomeTaxDeductedFromGrossIncome > profitsChargeable" in new Ct600FinalisationCalculator {
      computeTaxPayable(b70 = B70(BigDecimal("1000")), b79 = B79(None),
        b84 = B84(1500)) shouldBe B86(BigDecimal(0))
    }

    "return taxPayable when incomeTaxDeductedFromGrossIncome = 0 and profitsChargeable = 0" in new Ct600FinalisationCalculator {
      computeTaxPayable(b70 = B70(BigDecimal("0")), b79 = B79(None),
        b84 = B84(0)) shouldBe B86(BigDecimal(0))
    }

    "return taxPayable when incomeTaxDeductedFromGrossIncome < profitsChargeable" in new Ct600FinalisationCalculator {
      computeTaxPayable(b70 = B70(BigDecimal("1000")), b79 = B79(None),
        b84 = B84(450)) shouldBe B86(BigDecimal(550))
    }

    "return taxPayable when incomeTaxDeductedFromGrossIncome < profitsChargeable, with decimal values" in new Ct600FinalisationCalculator {
      computeTaxPayable(b70 = B70(BigDecimal("1000.80")), b79 = B79(None),
        b84 = B84(BigDecimal("450.30"))) shouldBe B86(BigDecimal("550.50"))
    }

    "return taxPayable when (incomeTaxDeductedFromGrossIncome + taxPayableUnderS419) < profitsChargeable, with decimal values" in new Ct600FinalisationCalculator {
      computeTaxPayable(b70 = B70(BigDecimal("440.30")), b79 = B79(Option(BigDecimal("10.10"))),
        b84 = B84(BigDecimal("450.30"))) shouldBe B86(BigDecimal("0.10"))
    }
  }

  "Ct600FinalisationCalculator - computeTaxRepayable" should {

    "return taxRepayable when incomeTaxDeductedFromGrossIncome > profitsChargeable" in new Ct600FinalisationCalculator {
      computeTaxRepayable(b70 = B70(BigDecimal("1000")), b79 = B79(None),
        b84 = B84(1500)) shouldBe B85(BigDecimal(500))
    }

    "return taxRepayable when incomeTaxDeductedFromGrossIncome > profitsChargeable, with decimal values" in new Ct600FinalisationCalculator {
      computeTaxRepayable(b70 = B70(BigDecimal("1000.30")), b79 = B79(None),
        b84 = B84(BigDecimal("1500.80"))) shouldBe B85(BigDecimal("500.50"))
    }

    "return taxRepayable when incomeTaxDeductedFromGrossIncome = 0 and profitsChargeable = 0" in new Ct600FinalisationCalculator {
      computeTaxRepayable(b70 = B70(BigDecimal("0")), b79 = B79(None),
        b84 = B84(0)) shouldBe B85(BigDecimal(0))
    }

    "return taxRepayable when incomeTaxDeductedFromGrossIncome < profitsChargeable" in new Ct600FinalisationCalculator {
      computeTaxRepayable(b70 = B70(BigDecimal("1000")), b79 = B79(None),
        b84 = B84(450)) shouldBe B85(BigDecimal(0))
    }

    "return taxRepayable when (incomeTaxDeductedFromGrossIncome + taxPayableUnderS419) > profitsChargeable, with decimal values" in new Ct600FinalisationCalculator {
      computeTaxRepayable(b70 = B70(BigDecimal("1000.30")), b79 = B79(Option(BigDecimal("10.10"))),
        b84 = B84(BigDecimal("1500.80"))) shouldBe B85(BigDecimal("490.40"))
    }
  }
}
