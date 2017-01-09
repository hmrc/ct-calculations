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
import org.scalatest.prop.TableDrivenPropertyChecks._
import uk.gov.hmrc.ct.computations.{CP1, CP2}

class PoolPercentageCalculatorSpec extends WordSpec with Matchers  {

  val calculator = PoolPercentageCalculator(oldMainRate = 20, newMainRate = 18, newRateStartDate =  new LocalDate("2012-01-01"))

  "days before" should {
    "return x days when start date is before change over date and end date is on change over" in {
      calculator.daysBefore(new LocalDate("2011-12-31"), new LocalDate("2012-01-01")) shouldBe 1
    }
    "return x days when start date is before change over date and end date is after change over" in {
      calculator.daysBefore(new LocalDate("2011-12-31"), new LocalDate("2012-01-02")) shouldBe 1
    }
    "return x days when startDate is before change over date and endDate is also before change over" in {
      calculator.daysBefore(new LocalDate("2011-12-01"), new LocalDate("2011-12-02")) shouldBe 2
    }
    "return 0 days when start date is the same as the change over date" in {
      calculator.daysBefore(new LocalDate("2012-01-01"), new LocalDate("2012-01-02")) shouldBe 0
    }
    "return 0 days when start date is after the change over date" in {
      calculator.daysBefore(new LocalDate("2012-01-02"), new LocalDate("2013-01-03")) shouldBe 0
    }
    "throw error when supplied startDate is after supplied endDate" in {
      an [IllegalArgumentException] should be thrownBy {
        calculator.daysBefore(new LocalDate("2011-12-02"), new LocalDate("2011-12-01"))
      }
    }
  }

  "days after" should {
    "return x days when startDate is on change over and endDate is after change over" in {
      calculator.daysOnAndAfter(new LocalDate("2012-01-01"), new LocalDate("2012-01-02")) shouldBe 2
    }
    "return x days when startDate is before change over and endDate is after change over" in {
      calculator.daysOnAndAfter(new LocalDate("2011-12-31"), new LocalDate("2012-01-02")) shouldBe 2
    }
    "return x days when startDate is after change over and end date is also after change over" in {
      calculator.daysOnAndAfter(new LocalDate("2012-01-02"), new LocalDate("2012-01-03")) shouldBe 2
    }
    "return 0 days when end date is before the change over date" in {
      calculator.daysOnAndAfter(new LocalDate("2011-12-01"), new LocalDate("2011-12-31")) shouldBe 0
    }
    "return 1 day when startDate and end date are both the same as the change over date" in {
      calculator.daysOnAndAfter(new LocalDate("2012-01-01"), new LocalDate("2012-01-01")) shouldBe 1
    }
    "throw error when suppied startDate is after the supplied endDate" in {
      an [IllegalArgumentException] should be thrownBy {
        calculator.daysOnAndAfter(new LocalDate("2012-01-31"), new LocalDate("2012-01-30"))
      }
    }
  }

  "apportionedMainRate" should {
    "return correct apportioned rate when evenly split across rate change date" in {
      calculator.apportionedMainRate(CP1(new LocalDate("2011-12-31")),CP2( new LocalDate("2012-01-01"))) shouldBe 19
    }
    "return correct apportioned rate when unevenly split across rate change date" in {
      calculator.apportionedMainRate(CP1(new LocalDate("2011-12-30")),CP2(new LocalDate("2012-01-01"))) shouldBe 19.33
    }
    "return correct apportioned rate when entirely within old range" in {
      calculator.apportionedMainRate(CP1(new LocalDate("2011-12-29")), CP2(new LocalDate("2011-12-31"))) shouldBe 20
    }
    "return correct apportioned rate when entirely within new range" in {
      calculator.apportionedMainRate(CP1(new LocalDate("2012-01-01")), CP2(new LocalDate("2012-01-03"))) shouldBe 18
    }
  }

  "apportionedSpecialRate" should {
    "return correct apportioned rate when evenly split across rate change date" in {
      calculator.apportionedSpecialRate(CP1(new LocalDate("2011-12-31")), CP2(new LocalDate("2012-01-01"))) shouldBe 9
    }
    "return correct apportioned rate when unevenly split across rate change date" in {
      calculator.apportionedSpecialRate(CP1(new LocalDate("2011-12-30")),  CP2(new LocalDate("2012-01-01"))) shouldBe 9.33
    }
    "return correct apportioned rate when entirely within old range" in {
      calculator.apportionedSpecialRate(CP1(new LocalDate("2011-11-29")),  CP2(new LocalDate("2011-11-30"))) shouldBe 10
    }
    "return correct apportioned rate when entirely within new range" in {
      calculator.apportionedSpecialRate(CP1(new LocalDate("2012-02-01")),  CP2(new LocalDate("2012-02-03"))) shouldBe 8
    }
  }

  val jiraAcceptanceTable = Table(
    ("startDate", "endDate", "mainPoolRate", "specialPoolRate"),
    ("2011-01-03", "2011-09-08", 20.00, 10.00),
    ("2012-01-01", "2012-12-31", 18.50, 8.50),
    ("2011-10-01", "2012-09-03", 19.08, 9.08),
    ("2011-10-01", "2012-09-30", 19.00, 9.00),
    ("2012-03-01", "2013-02-28", 18.17, 8.17),
    ("2012-06-09", "2013-04-25", 18.00, 8.00)
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
