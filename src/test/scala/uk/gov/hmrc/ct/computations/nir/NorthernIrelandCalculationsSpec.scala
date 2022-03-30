/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.nir

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP1, CP2, CP291, HmrcAccountingPeriod}
import uk.gov.hmrc.ct.ct600.calculations.NITradingProfitCalculationParameters
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3._

class NorthernIrelandCalculationsSpec extends WordSpec with NorthernIrelandCalculations with Matchers{
  "B360 = B355 * B350" in new CorporationTaxCalculator {

    calculateTaxForTradingProfitForFirstFinancialYear(B350(Some(40)), B355(Some(BigDecimal("40.41")))) shouldBe B360(Some(BigDecimal("1616.4")))
  }

  "B410 = B405 * B400" in new CorporationTaxCalculator {

    calculateTaxForTradingProfitForSecondFinancialYear(B400(Some(20)), B405(Some(BigDecimal("20.21")))) shouldBe B410(Some(BigDecimal("404.20")))
  }

  "B355" in new CorporationTaxCalculator {
    nIRrateOfTaxFy1(CP1(new LocalDate(2019, 4, 9))) shouldBe Some(0.125)
  }

  "B405" in new CorporationTaxCalculator {
    nIRrateOfTaxFy2(CP2(new LocalDate(2019, 4, 8))) shouldBe Some(0.125)
  }
  "B350 apportioned trading profit for FY1 when Northern Ireland is active" in new CorporationTaxCalculator {
    calculateNIApportionedTradingProfitsChargeableFy1(
      NITradingProfitCalculationParameters(CP291(Some(2000)),
        HmrcAccountingPeriod(CP1(new LocalDate(2018, 4, 12)), CP2(new LocalDate(2019, 4, 11))))) shouldBe 1940
  }

  "B400 apportioned trading profit for FY2 when Northern Ireland is active" in new CorporationTaxCalculator {
    calculateNIApportionedTradingProfitsChargeableFy2(
      NITradingProfitCalculationParameters(CP291(Some(3000)),
        HmrcAccountingPeriod(CP1(new LocalDate(2018, 4, 12)), CP2(new LocalDate(2019, 4, 11))))) shouldBe 90
  }
}
