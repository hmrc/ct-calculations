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

package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.capitalAllowanceAndSBA._
class SBACalculatorSpec extends WordSpec with Matchers {

  "SBA calculator" should {

    val cost: Int = 10000

    "apportion and calculate the right amount of sba claimable for a building whose AP is in the first 6 months during a leap year" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2019-01-01")
      val apEndDate: LocalDate = new LocalDate("2019-6-30")

      val firstUsageDate: LocalDate = new LocalDate("2019-01-01")

      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost shouldBe Some(99)
    }

    "apportion and calculate the right amount of sba claimable for a building for 12 months during a leap year" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2016-01-01")
      val apEndDate: LocalDate = new LocalDate("2016-12-31")

      val firstUsageDate: LocalDate = new LocalDate("2016-01-01")

      getDaysIntheYear(apStartDate) shouldBe 366

      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost  shouldBe Some(200)
    }

    "apportion and calculate the right amount of sba claimable for a building for 12 months during a leap year where accounting period starts before 1st April" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2016-02-29")
      val apEndDate: LocalDate = new LocalDate("2017-02-28")

      val firstUsageDate: LocalDate = new LocalDate("2016-02-29")

      getDaysIntheYear(apStartDate) shouldBe 366
      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost  shouldBe Some(200)
    }

    "apportion and calculate the right amount of sba claimable for a building for 12 months during a 2016 leap year where accounting period starts before 1st April" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2016-02-29")
      val apEndDate: LocalDate = new LocalDate("2017-02-28")

      val firstUsageDate: LocalDate = new LocalDate("2016-02-28")

      getDaysIntheYear(apStartDate) shouldBe 366
      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost  shouldBe Some(200)
    }

    "apportion and calculate the right amount of sba claimable for a building for 12 months during a leap year where accounting period starts on or after 1st April" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2016-03-01")
      val apEndDate: LocalDate = new LocalDate("2017-02-28")

      val firstUsageDate: LocalDate = new LocalDate("2016-02-28")

      getDaysIntheYear(apStartDate) shouldBe 365
      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost  shouldBe Some(200)
    }

    "apportion and calculate the right amount of sba claimable for a building for 3 months during a normal year where the contract date starts after AP start date." in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2019-01-01")
      val apEndDate: LocalDate = new LocalDate("2019-12-31")

      val firstUsageDate: LocalDate = new LocalDate("2019-10-01")

      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost  shouldBe Some(50)
    }

    "apportion and calculate the right amount of sba claimable for a building for 3 months during a leap year where the contract date starts after AP and start date including february" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2016-01-01")
      val apEndDate: LocalDate = new LocalDate("2016-12-31")

      val firstUsageDate: LocalDate = new LocalDate("2016-02-01")

      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost  shouldBe Some(183)
    }


    "apportion and calculate the right amount of sba claimable for a building for 3 months during a regular year where the contract date starts after AP start date including february" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2019-01-01")
      val apEndDate: LocalDate = new LocalDate("2019-12-31")

      val firstUsageDate: LocalDate = new LocalDate("2019-02-01")


      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost  shouldBe Some(183)
    }

    "apportion and calculate the right amount of sba claimable for a building for 3 months during a regular year where the contract date starts after AP start date not including february" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2019-01-01")
      val apEndDate: LocalDate = new LocalDate("2019-12-31")

      val firstUsageDate: LocalDate = new LocalDate("2019-03-01")

      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost  shouldBe Some(168)
    }

    "getDaysInTheYear produces correct amount of days for dates surrounding leap year" in new SBACalculator {
      getDaysIntheYear(new LocalDate("2020-01-01")) shouldBe 366
      getDaysIntheYear(new LocalDate("2020-02-28")) shouldBe 366
      getDaysIntheYear(new LocalDate("2020-02-29")) shouldBe 366
      getDaysIntheYear(new LocalDate("2020-03-01")) shouldBe 365
      getDaysIntheYear(new LocalDate("2100-01-01")) shouldBe 365
    }

    " apportion and calculate the right amount of sba claimable for a building"
      "calculate using the 2% rate if the the end date is before 2020-04-01" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2019-03-31")
      val apEndDate: LocalDate = new LocalDate("2020-03-31")

      val firstUsageDate: LocalDate = new LocalDate("2019-03-31")

      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost shouldBe Some(201)
    }

    "calculate just using the 3% rate if the start date is after 2020-04-01" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-04-01")
      val apEndDate: LocalDate = new LocalDate("2021-04-01")

      val firstUsageDate: LocalDate = new LocalDate("2020-04-01")
      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost))

      result.get.totalCost  shouldBe Some(301)
    }

    "calculate using both of the rates if the accounting period spans either side 2020-04-01" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-01-01")

      val apEndDate: LocalDate = new LocalDate("2020-12-31")

      val firstUsageDate: LocalDate = new LocalDate("2020-01-01")


      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost)).get

      result.ratePriorTaxYear2020.get.numberOfDaysRate  shouldBe 91
      result.ratePriorTaxYear2020.get.rateYearlyPercentage  shouldBe BigDecimal(0.02)


      result.ratePostTaxYear2020.get.numberOfDaysRate  shouldBe 275
      result.ratePostTaxYear2020.get.rateYearlyPercentage  shouldBe BigDecimal(0.03)

      result.totalCost  shouldBe Some(275)
    }

    "calculate using both of the rates if the accounting period spans either side 2020-04-01 and take  date into account usage" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2020-01-01")
      val apEndDate: LocalDate = new LocalDate("2020-12-31")
      val firstUsageDate: LocalDate = new LocalDate("2020-02-01")

      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost)).get

      result.ratePriorTaxYear2020.get.numberOfDaysRate  shouldBe 60
      result.ratePriorTaxYear2020.get.rateYearlyPercentage  shouldBe BigDecimal(0.02)
      result.ratePriorTaxYear2020.get.costRate  shouldBe 33

      result.ratePostTaxYear2020.get.numberOfDaysRate  shouldBe 275
      result.ratePostTaxYear2020.get.rateYearlyPercentage  shouldBe BigDecimal(0.03)
      result.ratePostTaxYear2020.get.costRate  shouldBe 225

      result.totalCost  shouldBe Some(258)
    }

    "calculate using the 3% rate taking into account the firstUsageDate" in new SBACalculator {

      val apStartDate: LocalDate = new LocalDate("2021-01-01")
      val apEndDate: LocalDate = new LocalDate("2021-12-31")

      val firstUsageDate: LocalDate = new LocalDate("2021-02-01")
      val result = getSbaDetails(apStartDate, apEndDate, Some(firstUsageDate), Option(cost)).get

      result.ratePriorTaxYear2020 shouldBe None

      result.ratePostTaxYear2020.get.numberOfDaysRate  shouldBe 334
      result.ratePostTaxYear2020.get.rateYearlyPercentage  shouldBe BigDecimal(0.03)
      result.ratePostTaxYear2020.get.costRate  shouldBe 275
      result.totalCost  shouldBe Some(275)
    }
  }
}
