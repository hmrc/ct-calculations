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

package uk.gov.hmrc.ct.ct600.v3.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.ct600.calculations.CorporationTaxCalculatorParameters
import uk.gov.hmrc.ct.ct600.v3._

class CorporationTaxCalculatorSpec extends WordSpec with Matchers {

  "B510" should {
    " = B475v3 + B480v3" in new CorporationTaxCalculator {
      calculateTaxChargeable(B475(BigDecimal("10.5")), B480(Some(BigDecimal("10.3")))) shouldBe B510(BigDecimal("20.8"))
    }
    " = B475v3 (B480 not present)" in new CorporationTaxCalculator {
      calculateTaxChargeable(B475(BigDecimal("10.5")), B480(None)) shouldBe B510(BigDecimal("10.5"))
    }
  }

  "B525 = MAX ( 0.00, B510v3 - B515v3 )" should {
    "return 0 where B510v3 - B515v3 is negative" in new CorporationTaxCalculator {
      calculateSATaxPayable(B510(BigDecimal("1")), B515(Some(BigDecimal("1.5")))) shouldBe B525(BigDecimal("0"))
    }
    "return difference where B510v3 - B515v3 is positive" in new CorporationTaxCalculator {
      calculateSATaxPayable(B510(BigDecimal("1.5")), B515(Some(BigDecimal("1.0")))) shouldBe B525(BigDecimal("0.5"))
    }
  }

  "B430 = B345v3 + B395v3" in new CorporationTaxCalculator {
    calculateCorporationTax(B345(BigDecimal("10.5")),B395(BigDecimal("10.3"))) shouldBe B430(BigDecimal("20.8"))
  }

  "B345 = B335v3 * B340v3" in new CorporationTaxCalculator {
    calculateTaxForFirstFinancialYear(B335(10),B340(BigDecimal("10.11"))) shouldBe B345(BigDecimal("101.1"))
  }

  "B330" in new CorporationTaxCalculator {
    financialYear1(HmrcAccountingPeriod(new CP1(new LocalDate(2014, 6, 1)),new CP2(new LocalDate(2015, 5, 31)) )) shouldBe 2014
  }

  "B380" should {
    "be none when period end is in same financial year" in new CorporationTaxCalculator {
      financialYear2(HmrcAccountingPeriod(new CP1(new LocalDate(2014, 6, 1)), new CP2(new LocalDate(2014, 5, 31)))) shouldBe None
    }
    "be the following year when period end in next financial year" in new CorporationTaxCalculator {
      financialYear2(HmrcAccountingPeriod(new CP1(new LocalDate(2014, 6, 1)), new CP2(new LocalDate(2015, 5, 31)))) shouldBe Some(2015)
    }
  }

  "B395 = B385v3 * B390v3" in new CorporationTaxCalculator {
    calculateTaxForSecondFinancialYear(B385(10), B390(BigDecimal("11.11"))) shouldBe B395(BigDecimal("111.1"))
  }

  // These tests assume that delegate code is tested thoroughly by v2 tests
  "B340" in new CorporationTaxCalculator {
    rateOfTaxFy1(new CP1(new LocalDate(2014, 4, 1))) shouldBe 0.21
  }

  "B390" in new CorporationTaxCalculator {
    rateOfTaxFy2(new CP2(new LocalDate(2015, 4, 1))) shouldBe 0.20
  }

  "B335 apportioned profits chargeable FY1" in new CorporationTaxCalculator {
    calculateApportionedProfitsChargeableFy1(
      CorporationTaxCalculatorParameters(CP295(20000),
        HmrcAccountingPeriod(CP1(new LocalDate(2014, 10, 1)), CP2(new LocalDate(2015, 9, 30))))) shouldBe B335(9973)
  }

  "B385 apportioned profits chargeable FY2" in new CorporationTaxCalculator {
    calculateApportionedProfitsChargeableFy2(
      CorporationTaxCalculatorParameters(CP295(20000),
        HmrcAccountingPeriod(CP1(new LocalDate(2014, 10, 1)), CP2(new LocalDate(2015, 9, 30))))) shouldBe B385(10027)
  }
  // ----------------------------------------------------------------------

  "B520v3 = B515v3 - B510v3" should {
    "return result if non negative" in new CorporationTaxCalculator {
      calculateIncomeTaxRepayable(B515(Some(BigDecimal("5.5"))), B510(BigDecimal("1.0"))) shouldBe B520(Some(BigDecimal("4.5")))
      calculateIncomeTaxRepayable(B515(Some(BigDecimal("1.0"))), B510(BigDecimal("1.0"))) shouldBe B520(Some(BigDecimal(0)))
    }
    "not be present if result is negative" in new CorporationTaxCalculator {
      calculateIncomeTaxRepayable(B515(Some(BigDecimal("1.0"))), B510(BigDecimal("1.1"))) shouldBe B520(None)
    }
  }

  "B600v3 = B525v3 - B595v3" should {
    "return result if non negative" in new CorporationTaxCalculator {
      calculateTotalTaxToPay(B525(BigDecimal("5.5")), B595(Some(BigDecimal("1.0")))) shouldBe B600(Some(BigDecimal("4.5")))
      calculateTotalTaxToPay(B525(BigDecimal("1.0")), B595(Some(BigDecimal("1.0")))) shouldBe B600(Some(BigDecimal(0)))
    }
    "not be present if result is negative" in new CorporationTaxCalculator {
      calculateTotalTaxToPay(B525(BigDecimal("1.0")), B595(Some(BigDecimal("1.1")))) shouldBe B600(None)
    }
  }

  "B605v3 = B595v3 - B525v3" should {
    "return result if non negative" in new CorporationTaxCalculator {
      calculateTaxOverpaid(B595(Some(BigDecimal("5.5"))), B525(BigDecimal("1.0"))) shouldBe B605(Some(BigDecimal("4.5")))
      calculateTaxOverpaid(B595(Some(BigDecimal("1.0"))), B525(BigDecimal("1.0"))) shouldBe B605(Some(BigDecimal(0)))
    }
    "not be present if result is negative" in new CorporationTaxCalculator {
      calculateTaxOverpaid(B595(Some(BigDecimal("1.0"))), B525(BigDecimal("1.1"))) shouldBe B605(None)
    }
  }

  "B280 - Amounts carried back from later periods" should {

    "be true if CP286 > 0" in new CorporationTaxCalculator {
      areAmountsCarriedBackFromLaterPeriods(CP286(Some(20))) shouldBe B280(true)
    }

    "be false if CP286 == 0" in new CorporationTaxCalculator {
      areAmountsCarriedBackFromLaterPeriods(CP286(Some(0))) shouldBe B280(false)
    }

    "be false if CP286 < 0" in new CorporationTaxCalculator {
      areAmountsCarriedBackFromLaterPeriods(CP286(Some(-20))) shouldBe B280(false)
    }

    "be false if there is no CP286" in new CorporationTaxCalculator {
      areAmountsCarriedBackFromLaterPeriods(CP286(None)) shouldBe B280(false)
    }

  }

  "B45" should {
    "be same as B45Inputs if CP287 == 0" in new CorporationTaxCalculator {
      defaultSetIfLossCarriedForward(B45Input(None), CP287(0)) shouldBe B45(None)
      defaultSetIfLossCarriedForward(B45Input(Some(true)), CP287(0)) shouldBe B45(Some(true))
      defaultSetIfLossCarriedForward(B45Input(Some(false)), CP287(0)) shouldBe B45(Some(false))
    }

    "be true if CP287 > 0" in new CorporationTaxCalculator {
      defaultSetIfLossCarriedForward(B45Input(None), CP287(99)) shouldBe B45(Some(true))
      defaultSetIfLossCarriedForward(B45Input(Some(true)), CP287(99)) shouldBe B45(Some(true))
      defaultSetIfLossCarriedForward(B45Input(Some(false)), CP287(99)) shouldBe B45(Some(true))
    }
  }

  "B528" should {
    "be equal to the sum of B525 and B527 if both are set" in new CorporationTaxCalculator {
      calculateSelfAssessmentOfTaxPayable(B525(10), B527(Some(5))) shouldBe B528(Some(15))
    }

    "be equal to B525 if only B525 is set" in new CorporationTaxCalculator {
      calculateSelfAssessmentOfTaxPayable(B525(12), B527(None)) shouldBe B528(Some(12))
    }
  }

  class Calc extends CorporationTaxCalculator
}
