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

package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO02
import uk.gov.hmrc.ct.computations.{CP1, CP2}

class AnnualInvestmentAllowanceCalculatorSpec extends WordSpec with Matchers {

  "AnnualInvestmentAllowanceCalculator.maximum using real periods" should {
    val amounts = AnnualInvestmentAllowancePeriods()

    "Examples from AC" in new AnnualInvestmentAllowanceCalculator {
      maximum(CP1(new LocalDate(2010, 1, 1)), CP2(new LocalDate(2010, 12, 31)), amounts) shouldBe CATO02(87672)
      maximum(CP1(new LocalDate(2010, 2, 1)), CP2(new LocalDate(2010, 4, 30)), amounts) shouldBe CATO02(16668)
      maximum(CP1(new LocalDate(2010, 4, 1)), CP2(new LocalDate(2010, 12, 31)), amounts) shouldBe CATO02(75343)
      maximum(CP1(new LocalDate(2012, 1, 1)), CP2(new LocalDate(2012, 12, 31)), amounts) shouldBe CATO02(43768)
      maximum(CP1(new LocalDate(2012, 2, 1)), CP2(new LocalDate(2012, 4, 30)), amounts) shouldBe CATO02(18751)
      maximum(CP1(new LocalDate(2012, 4, 1)), CP2(new LocalDate(2013, 3, 31)), amounts) shouldBe CATO02(81250)
      maximum(CP1(new LocalDate(2012, 3, 1)), CP2(new LocalDate(2013, 2, 28)), amounts) shouldBe CATO02(68751)
      maximum(CP1(new LocalDate(2012, 3, 1)), CP2(new LocalDate(2013, 1, 31)), amounts) shouldBe CATO02(48563)
      maximum(CP1(new LocalDate(2015, 4, 1)), CP2(new LocalDate(2016, 3, 31)), amounts) shouldBe CATO02(426577)
      maximum(CP1(new LocalDate(2015, 6, 1)), CP2(new LocalDate(2016, 3, 31)), amounts) shouldBe CATO02(343015)
      maximum(CP1(new LocalDate(2014, 1, 1)), CP2(new LocalDate(2014, 12, 31)), amounts) shouldBe CATO02(438357)
      maximum(CP1(new LocalDate(2014, 4, 6)), CP2(new LocalDate(2015, 4, 5)), amounts) shouldBe CATO02(500000)
    }

    "for a non leap year, return 365 as the year length for apportioning" in new AnnualInvestmentAllowanceCalculator {
      yearLengthForDailyBasis(new LocalDate(2011, 1, 1), new LocalDate(2011, 12, 31)) shouldBe 365
    }

    "for a AP covering a leap year which doesn't include the start date of any AIA Period, return 366 as the year length for apportioning" in new AnnualInvestmentAllowanceCalculator {
      yearLengthForDailyBasis(new LocalDate(2011, 4, 1), new LocalDate(2012, 3, 31)) shouldBe 366
    }

    "for a AP covering a leap year which includes the start date of any AIA Period, return 365 as the year length for apportioning" in new AnnualInvestmentAllowanceCalculator {
      yearLengthForDailyBasis(new LocalDate(2011, 5, 1), new LocalDate(2012, 4, 30)) shouldBe 365
    }

    "for a AP covering a leap year which ends on the start date of any AIA Period, return 365 as the year length for apportioning" in new AnnualInvestmentAllowanceCalculator {
      yearLengthForDailyBasis(new LocalDate(2011, 5, 1), new LocalDate(2012, 4, 1)) shouldBe 365
    }
  }
}
