/*
 * Copyright 2019 HM Revenue & Customs
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
import org.scalatest.prop.TableDrivenPropertyChecks._
import uk.gov.hmrc.ct.computations.{CP1, CP2}

class PoolPercentageCalculatorSpec extends WordSpec with Matchers  {

  val calculator = PoolPercentageCalculator(oldMainRate = 18, newMainRate = 18, oldSpecialRate = 8, newSpecialRate = 6, newRateStartDate =  new LocalDate("2019-01-01"))

  "days before" should {
    "return x days when start date is before change over date and end date is on change over" in {
      calculator.daysBefore(new LocalDate("2018-12-31"), new LocalDate("2019-01-01")) shouldBe 1
    }
    "return x days when start date is before change over date and end date is after change over" in {
      calculator.daysBefore(new LocalDate("2018-12-31"), new LocalDate("2019-01-02")) shouldBe 1
    }
    "return x days when startDate is before change over date and endDate is also before change over" in {
      calculator.daysBefore(new LocalDate("2018-12-01"), new LocalDate("2018-12-02")) shouldBe 2
    }
    "return 0 days when start date is the same as the change over date" in {
      calculator.daysBefore(new LocalDate("2019-01-01"), new LocalDate("2019-01-02")) shouldBe 0
    }
    "return 0 days when start date is after the change over date" in {
      calculator.daysBefore(new LocalDate("2019-01-02"), new LocalDate("2020-01-03")) shouldBe 0
    }
    "throw error when supplied startDate is after supplied endDate" in {
      an [IllegalArgumentException] should be thrownBy {
        calculator.daysBefore(new LocalDate("2018-12-02"), new LocalDate("2018-12-01"))
      }
    }
  }

  "days after" should {
    "return x days when startDate is on change over and endDate is after change over" in {
      calculator.daysOnAndAfter(new LocalDate("2019-01-01"), new LocalDate("2019-01-02")) shouldBe 2
    }
    "return x days when startDate is before change over and endDate is after change over" in {
      calculator.daysOnAndAfter(new LocalDate("2018-12-31"), new LocalDate("2019-01-02")) shouldBe 2
    }
    "return x days when startDate is after change over and end date is also after change over" in {
      calculator.daysOnAndAfter(new LocalDate("2019-01-02"), new LocalDate("2019-01-03")) shouldBe 2
    }
    "return 0 days when end date is before the change over date" in {
      calculator.daysOnAndAfter(new LocalDate("2018-12-01"), new LocalDate("2018-12-31")) shouldBe 0
    }
    "return 1 day when startDate and end date are both the same as the change over date" in {
      calculator.daysOnAndAfter(new LocalDate("2019-01-01"), new LocalDate("2019-01-01")) shouldBe 1
    }
    "throw error when suppied startDate is after the supplied endDate" in {
      an [IllegalArgumentException] should be thrownBy {
        calculator.daysOnAndAfter(new LocalDate("2019-01-31"), new LocalDate("2019-01-30"))
      }
    }
  }

  "apportionedMainRate" should {
    "return correct apportioned rate when evenly split across rate change date" in {
      calculator.apportionedMainRate(CP1(new LocalDate("2018-12-31")),CP2( new LocalDate("2019-01-01"))) shouldBe 18
    }
    "return correct apportioned rate when unevenly split across rate change date" in {
      calculator.apportionedMainRate(CP1(new LocalDate("2018-12-30")),CP2(new LocalDate("2019-01-01"))) shouldBe 18
    }
    "return correct apportioned rate when entirely within old range" in {
      calculator.apportionedMainRate(CP1(new LocalDate("2018-12-29")), CP2(new LocalDate("2018-12-31"))) shouldBe 18
    }
    "return correct apportioned rate when entirely within new range" in {
      calculator.apportionedMainRate(CP1(new LocalDate("2019-01-01")), CP2(new LocalDate("2019-01-03"))) shouldBe 18
    }
  }

  "apportionedSpecialRate" should {
    "return correct apportioned rate when evenly split across rate change date" in {
      calculator.apportionedSpecialRate(CP1(new LocalDate("2018-12-31")), CP2(new LocalDate("2019-01-01"))) shouldBe 7
    }
    "return correct apportioned rate when unevenly split across rate change date" in {
      calculator.apportionedSpecialRate(CP1(new LocalDate("2018-12-30")),  CP2(new LocalDate("2019-01-01"))) shouldBe 7.33
    }
    "return correct apportioned rate when entirely within old range" in {
      calculator.apportionedSpecialRate(CP1(new LocalDate("2018-11-29")),  CP2(new LocalDate("2018-11-30"))) shouldBe 8
    }
    "return correct apportioned rate when entirely within new range" in {
      calculator.apportionedSpecialRate(CP1(new LocalDate("2019-02-01")),  CP2(new LocalDate("2019-02-03"))) shouldBe 6
    }
  }

  val jiraAcceptanceTable = Table(
    ("startDate", "endDate", "mainPoolRate", "specialPoolRate"),
    ("2018-01-03", "2018-09-08", 18.00, 8.00),
    ("2019-01-01", "2019-12-31", 18.00, 6.49),
    ("2018-10-01", "2019-09-03", 18.00, 7.08),
    ("2018-10-01", "2019-09-30", 18.00, 7.00),
    ("2019-03-01", "2020-02-28", 18.00, 6.17),
    ("2019-06-09", "2020-04-25", 18.00, 6.00),
    ("2019-04-04", "2019-04-05", 18.00, 6.00),
    ("2019-01-01", "2019-03-31", 18.00, 8.00),
    ("2019-01-01", "2019-12-31", 18.00, 6.49)
  )

  "apportionedMainRate" should {
    "pass the acceptance criteria from Jira CATO-2553" in new PoolPercentageCalculator {
      forAll(jiraAcceptanceTable) {
        (startDate: String, endDate: String, mainPoolRate: Double, specialPoolRate: Double) => {
          apportionedMainRate(CP1(new LocalDate(startDate)),CP2(new LocalDate(endDate))) shouldBe BigDecimal(mainPoolRate)
          apportionedSpecialRate(CP1(new LocalDate(startDate)),CP2( new LocalDate(endDate))) shouldBe BigDecimal(specialPoolRate)
        }
      }
    }
  }
}
