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
import uk.gov.hmrc.ct.accounts.{AC12, AC4, AC3}
import uk.gov.hmrc.ct.computations.{CP1, CP2}

class ApportionedTurnoverCalculatorSpec extends WordSpec with Matchers {

  // Filing period that is 18 months long and contains a leap year
  val ac3 = AC3(new LocalDate(2012, 1, 1))
  val ac4 = AC4(new LocalDate(2013, 6, 30))

  "Apportioned Turnover Calculator" should {

    "apportion turnover before, during and after accounting period and reapportion even residual to periods before and after accounting period" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 10234

      val result = apportionPeriodOfAccountsTurnover(
        ac3,
        ac4,
        CP1(new LocalDate(2012, 4, 1)),
        CP2(new LocalDate(2013, 3, 31)),
        AC12(periodOfAccountsTurnover)
      )

      result shouldBe ApportionedTurnover(Some(1703), Some(6828), Some(1703))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion turnover before, during and after accounting period and reapportion odd residual to period before accounting period" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 10233

      val result = apportionPeriodOfAccountsTurnover(
        ac3,
        ac4,
        CP1(new LocalDate(2012, 4, 1)),
        CP2(new LocalDate(2013, 3, 31)),
        AC12(periodOfAccountsTurnover)
      )

      result shouldBe ApportionedTurnover(Some(1703), Some(6828), Some(1702))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion turnover before, during and after two day accounting period and reapportion odd residual to period before accounting period" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 10234

      val result = apportionPeriodOfAccountsTurnover(
        ac3,
        ac4,
        CP1(new LocalDate(2012, 4, 1)),
        CP2(new LocalDate(2012, 4, 2)),
        AC12(periodOfAccountsTurnover)
      )

      result shouldBe ApportionedTurnover(Some(1703), Some(37), Some(8494))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion turnover for period of accounts of 1st April 2013 to 31st March 2014 and accounting period of 1st May 2013 to 31st December 2013" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 648300

      val result = apportionPeriodOfAccountsTurnover(
        AC3(new LocalDate(2013, 4, 1)),
        AC4(new LocalDate(2014, 3, 31)),
        CP1(new LocalDate(2013, 5, 1)),
        CP2(new LocalDate(2013, 12, 31)),
        AC12(periodOfAccountsTurnover)
      )

      result shouldBe ApportionedTurnover(Some(53285), Some(435160), Some(159855))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion a turnover of zero as Some(0) rather than None" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 0

      val result = apportionPeriodOfAccountsTurnover(
        AC3(new LocalDate(2013, 4, 1)),
        AC4(new LocalDate(2014, 3, 31)),
        CP1(new LocalDate(2013, 5, 1)),
        CP2(new LocalDate(2013, 12, 31)),
        AC12(periodOfAccountsTurnover)
      )

      result shouldBe ApportionedTurnover(Some(0), Some(0), Some(0))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion a negative turnover as Some(0) " in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = -100

      val result = apportionPeriodOfAccountsTurnover(
        AC3(new LocalDate(2013, 4, 1)),
        AC4(new LocalDate(2014, 3, 31)),
        CP1(new LocalDate(2013, 5, 1)),
        CP2(new LocalDate(2013, 12, 31)),
        AC12(periodOfAccountsTurnover)
      )

      result shouldBe ApportionedTurnover(Some(0), Some(0), Some(0))
      result.total shouldBe 0
    }

    "apportion turnover to periods before and after of length zero days as None rather than Some(0)" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 7

      val result = apportionPeriodOfAccountsTurnover(
        AC3(new LocalDate(2012, 1, 1)),
        AC4(new LocalDate(2012, 1, 1)),
        CP1(new LocalDate(2012, 1, 1)),
        CP2(new LocalDate(2012, 1, 1)),
        AC12(periodOfAccountsTurnover)
      )

      result shouldBe ApportionedTurnover(None, Some(7), None)
      result.total shouldBe periodOfAccountsTurnover
    }

    "throw an exception when accounting period is outside the period of accounts" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 10234

      val ac3 = AC3(new LocalDate(2012, 1, 1))
      val ac4 = AC4(new LocalDate(2012, 1, 2))

      intercept[IllegalStateException] {
        apportionPeriodOfAccountsTurnover(
          ac3,
          ac4,
          CP1(new LocalDate(2011, 12, 31)),
          CP2(new LocalDate(2012, 1, 2)),
          AC12(periodOfAccountsTurnover)
        )
      }

      intercept[IllegalStateException] {
        apportionPeriodOfAccountsTurnover(
          ac3,
          ac4,
          CP1(new LocalDate(2011, 12, 31)),
          CP2(new LocalDate(2012, 1, 2)),
          AC12(periodOfAccountsTurnover)
        )
      }

      intercept[IllegalStateException] {
        apportionPeriodOfAccountsTurnover(
          ac3,
          ac4,
          CP1(new LocalDate(2012, 1, 1)),
          CP2(new LocalDate(2012, 1, 3)),
          AC12(periodOfAccountsTurnover)
        )
      }

      intercept[IllegalStateException] {
        apportionPeriodOfAccountsTurnover(
          ac3,
          ac4,
          CP1(new LocalDate(2011, 12, 31)),
          CP2(new LocalDate(2012, 1, 3)),
          AC12(periodOfAccountsTurnover)
        )
      }
    }
  }
}
