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

package uk.gov.hmrc.ct.accounts.frsse2008.calculations

import org.joda.time.LocalDate
import org.scalatest.{WordSpec, Matchers}

class PeriodCalculatorSpec extends WordSpec with Matchers {

  "periodHeading" should {

    "return heading components containing multiple months" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2014, 7, 1)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 6
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe "periodHeader.plural"
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "1 Jul 2014"
    }

    "return heading components containing a single month" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2014, 2, 1)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 1
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe "periodHeader.singular"
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "1 Feb 2014"
    }

    "return heading components containing a partial month with only 15 days" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2014, 2, 15)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 1
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe "periodHeader.singular"
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "15 Feb 2014"
    }

    "return heading components containing a partial month with only 16 days" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2014, 2, 16)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 2
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe "periodHeader.plural"
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "16 Feb 2014"
    }

    "return heading components where the period is 12 months" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2015, 1, 1)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 12
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe ""
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "2015"
    }

    "return heading components where the period is greater than 12 months" in {
      val startDate = new LocalDate(2014, 1, 1)
      val endDate = new LocalDate(2015, 4, 30)

      PeriodCalculator.periodHeadingComponents(startDate, endDate).monthCount shouldBe 16
      PeriodCalculator.periodHeadingComponents(startDate, endDate).messageKey shouldBe "periodHeader.plural"
      PeriodCalculator.periodHeadingComponents(startDate, endDate).dateText shouldBe "30 Apr 2015"
    }
  }

}
