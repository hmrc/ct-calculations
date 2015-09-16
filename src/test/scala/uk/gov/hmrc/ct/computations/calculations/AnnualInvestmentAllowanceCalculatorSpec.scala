/*
 * Copyright 2015 HM Revenue & Customs
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
      maximum(CP1(LocalDate.parse("2010-01-01")), CP2(LocalDate.parse("2010-12-31")), amounts) shouldBe CATO02(87672)
      maximum(CP1(LocalDate.parse("2010-02-01")), CP2(LocalDate.parse("2010-04-30")), amounts) shouldBe CATO02(16303)
      maximum(CP1(LocalDate.parse("2010-04-01")), CP2(LocalDate.parse("2010-12-31")), amounts) shouldBe CATO02(75343)
      maximum(CP1(LocalDate.parse("2012-01-01")), CP2(LocalDate.parse("2012-12-31")), amounts) shouldBe CATO02(43768)
      maximum(CP1(LocalDate.parse("2012-02-01")), CP2(LocalDate.parse("2012-04-30")), amounts) shouldBe CATO02(18494)
      maximum(CP1(LocalDate.parse("2012-04-01")), CP2(LocalDate.parse("2013-03-31")), amounts) shouldBe CATO02(80480)
      maximum(CP1(LocalDate.parse("2012-03-01")), CP2(LocalDate.parse("2013-02-28")), amounts) shouldBe CATO02(67741)
      maximum(CP1(LocalDate.parse("2012-03-01")), CP2(LocalDate.parse("2013-01-31")), amounts) shouldBe CATO02(48563)
      maximum(CP1(LocalDate.parse("2015-04-01")), CP2(LocalDate.parse("2016-03-31")), amounts) shouldBe CATO02(426577)
      maximum(CP1(LocalDate.parse("2015-06-01")), CP2(LocalDate.parse("2016-03-31")), amounts) shouldBe CATO02(343015)
      maximum(CP1(LocalDate.parse("2014-01-01")), CP2(LocalDate.parse("2014-12-31")), amounts) shouldBe CATO02(438357)
      maximum(CP1(LocalDate.parse("2014-04-06")), CP2(LocalDate.parse("2015-04-05")), amounts) shouldBe CATO02(500001)
    }

    "for a non leap year, return 365 as the year length for apportioning" in new AnnualInvestmentAllowanceCalculator {
        yearLengthForApportioning(LocalDate.parse("2011-01-01"), LocalDate.parse("2011-12-31")) shouldBe 365
    }

    "for a AP covering a leap year which doesn't include the start date of any AIA Period, return 366 as the year length for apportioning" in new AnnualInvestmentAllowanceCalculator {
      yearLengthForApportioning(LocalDate.parse("2011-04-01"), LocalDate.parse("2012-03-31")) shouldBe 366
    }

    "for a AP covering a leap year which includes the start date of any AIA Period, return 365 as the year length for apportioning" in new AnnualInvestmentAllowanceCalculator {
      yearLengthForApportioning(LocalDate.parse("2011-05-01"), LocalDate.parse("2012-04-30")) shouldBe 365
    }

    "for a AP covering a leap year which ends on the start date of any AIA Period, return 365 as the year length for apportioning" in new AnnualInvestmentAllowanceCalculator {
      yearLengthForApportioning(LocalDate.parse("2011-05-01"), LocalDate.parse("2012-04-01")) shouldBe 365
    }
  }
}
