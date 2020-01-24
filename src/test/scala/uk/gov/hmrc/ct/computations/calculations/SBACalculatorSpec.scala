/*
 * Copyright 2020 HM Revenue & Customs
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

class SBACalculatorSpec extends WordSpec with Matchers {

  "SBA calculator" should {
    "apportion and calculate the right amount of sba claimable for a building whose AP is in the first 6 months during a leap year" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-01-01")
      val endDate: LocalDate = new LocalDate("2020-6-30")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-01-01")
      val firstUsageDate: LocalDate = new LocalDate("2021-01-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, Option(contractStartDate), Some(firstUsageDate), Option(cost))

      result shouldBe Some(99)
    }

    "apportion and calculate the right amount of sba claimable for a building for 12 months during a leap year" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-01-01")
      val endDate: LocalDate = new LocalDate("2020-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-01-01")
      val firstUsageDate: LocalDate = new LocalDate("2021-01-01")

      override val rate: BigDecimal = 0.02

      getDaysIntheYear(apStartDate) shouldBe 366

      val result = getAmountClaimableForSBA(apStartDate, endDate, Option(contractStartDate), Some(firstUsageDate), Option(cost))

      result shouldBe Some(200)
    }

    "apportion and calculate the right amount of sba claimable for a building for 12 months during a leap year where accounting period starts before 1st April" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-02-29")
      val endDate: LocalDate = new LocalDate("2021-02-28")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-02-29")
      val firstUsageDate: LocalDate = new LocalDate("2021-02-28")

      override val rate: BigDecimal = 0.02

      getDaysIntheYear(apStartDate) shouldBe 366
      val result = getAmountClaimableForSBA(apStartDate, endDate, Option(contractStartDate), Some(firstUsageDate), Option(cost))

      result shouldBe Some(200)
    }

    "apportion and calculate the right amount of sba claimable for a building for 12 months during a 2024 leap year where accounting period starts before 1st April" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2024-02-29")
      val endDate: LocalDate = new LocalDate("2025-02-28")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2024-02-29")
      val firstUsageDate: LocalDate = new LocalDate("2025-02-28")

      override val rate: BigDecimal = 0.02

      getDaysIntheYear(apStartDate) shouldBe 366
      val result = getAmountClaimableForSBA(apStartDate, endDate, Option(contractStartDate), Some(firstUsageDate), Option(cost))

      result shouldBe Some(200)
    }

    "apportion and calculate the right amount of sba claimable for a building for 12 months during a leap year where accounting period starts on or after 1st April" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-03-01")
      val endDate: LocalDate = new LocalDate("2021-02-28")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-02-29")
      val firstUsageDate: LocalDate = new LocalDate("2021-02-28")

      override val rate: BigDecimal = 0.02

      getDaysIntheYear(apStartDate) shouldBe 365
      val result = getAmountClaimableForSBA(apStartDate, endDate, Option(contractStartDate), Some(firstUsageDate), Option(cost))

      result shouldBe Some(200)
    }

    "apportion and calculate the right amount of sba claimable for a building for 3 months during a normal year where the contract date starts after AP start date." in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-01-01")
      val endDate: LocalDate = new LocalDate("2020-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-10-01")
      val firstUsageDate: LocalDate = new LocalDate("2021-10-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, Option(contractStartDate), Some(firstUsageDate), Option(cost))

      result shouldBe Some(50)
    }

    "apportion and calculate the right amount of sba claimable for a building for 3 months during a leap year where the contract date starts after AP and start date including february" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-01-01")
      val endDate: LocalDate = new LocalDate("2020-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2020-02-01")
      val firstUsageDate: LocalDate = new LocalDate("2021-02-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, Option(contractStartDate), Some(firstUsageDate), Option(cost))

      result shouldBe Some(183)
    }


    "apportion and calculate the right amount of sba claimable for a building for 3 months during a regular year where the contract date starts after AP start date including february" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2021-01-01")
      val endDate: LocalDate = new LocalDate("2021-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2021-02-01")
      val firstUsageDate: LocalDate = new LocalDate("2022-02-01")


      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, Option(contractStartDate), Some(firstUsageDate), Option(cost))

      result shouldBe Some(183)
    }

    "apportion and calculate the right amount of sba claimable for a building for 3 months during a regular year where the contract date starts after AP start date not including february" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2021-01-01")
      val endDate: LocalDate = new LocalDate("2021-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2021-03-01")
      val firstUsageDate: LocalDate = new LocalDate("2022-03-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, Option(contractStartDate), Some(firstUsageDate), Option(cost))

      result shouldBe Some(168)
    }

    "apportion and calculate the right amount using the firstUsageDate if it is before the contract date" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2021-01-01")
      val endDate: LocalDate = new LocalDate("2021-12-31")
      val cost: Int = 10000
      val contractStartDate: LocalDate = new LocalDate("2021-12-01")
      val firstUsageDate: LocalDate = new LocalDate("2021-03-01")

      override val rate: BigDecimal = 0.02

      val result = getAmountClaimableForSBA(apStartDate, endDate, Option(contractStartDate), Some(firstUsageDate), Option(cost))

      result shouldBe Some(168)
    }
  }
}
