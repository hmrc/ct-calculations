/*
 * Copyright 2024 HM Revenue & Customs
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

import java.time.LocalDate
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.accounts.{AC12, AC3, AC4, AC401, AC403}
import uk.gov.hmrc.ct.computations.{CP1, CP2}

class ApportionedTurnoverCalculatorSpec extends AnyWordSpec with Matchers {

  // Filing period that is 18 months long and contains a leap year
  val ac3 = AC3(LocalDate.of(2012,1,1))
  val ac4 = AC4(LocalDate.of(2013,6,30))

  "Apportioned Turnover Calculator" should {

    "apportion turnover before, during and after accounting period and reapportion even residual to periods before and after accounting period" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 10234

      val result = apportionPeriodOfAccountsTurnover(
        ac3,
        ac4,
        CP1(LocalDate.of(2012,4,1)),
        CP2(LocalDate.of(2013,3,31)),
        AC12(periodOfAccountsTurnover),
        AC401(None)
      )

      result shouldBe ApportionedTurnover(Some(1703), Some(6828), Some(1703))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion turnover before, during and after accounting period and reapportion odd residual to period before accounting period" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 10233

      val result = apportionPeriodOfAccountsTurnover(
        ac3,
        ac4,
        CP1(LocalDate.of(2012,4,1)),
        CP2(LocalDate.of(2013,3,31)),
        AC12(periodOfAccountsTurnover),
        AC401(None)
      )

      result shouldBe ApportionedTurnover(Some(1703), Some(6828), Some(1702))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion turnover before, during and after two day accounting period and reapportion odd residual to period before accounting period" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 10234

      val result = apportionPeriodOfAccountsTurnover(
        ac3,
        ac4,
        CP1(LocalDate.of(2012,4,1)),
        CP2(LocalDate.of(2012,4,2)),
        AC12(periodOfAccountsTurnover),
        AC401(None)
      )

      result shouldBe ApportionedTurnover(Some(1703), Some(37), Some(8494))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion turnover for period of accounts of 1st April 2013 to 31st March 2014 and accounting period of 1st May 2013 to 31st December 2013" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 648300

      val result = apportionPeriodOfAccountsTurnover(
        AC3(LocalDate.of(2013,4,1)),
        AC4(LocalDate.of(2014,3,31)),
        CP1(LocalDate.of(2013,5,1)),
        CP2(LocalDate.of(2013,12,31)),
        AC12(periodOfAccountsTurnover),
        AC401(None)
      )

      result shouldBe ApportionedTurnover(Some(53285), Some(435160), Some(159855))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion turnover for period of accounts of 1st April 2013 to 31st March 2014 and accounting period of 1st May 2013 to 31st December 2013 for a negative number" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = -648300

      val result = apportionPeriodOfAccountsTurnover(
        AC3(LocalDate.of(2013,4,1)),
        AC4(LocalDate.of(2014,3,31)),
        CP1(LocalDate.of(2013,5,1)),
        CP2(LocalDate.of(2013,12,31)),
        AC12(periodOfAccountsTurnover),
        AC401(None)
      )

      result shouldBe ApportionedTurnover(Some(-53285), Some(-435160), Some(-159855))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion a turnover of zero as Some(0) rather than None" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 0

      val result = apportionPeriodOfAccountsTurnover(
        AC3(LocalDate.of(2013,4,1)),
        AC4(LocalDate.of(2014,3,31)),
        CP1(LocalDate.of(2013,5,1)),
        CP2(LocalDate.of(2013,12,31)),
        AC12(periodOfAccountsTurnover),
        AC401(None)
      )

      result shouldBe ApportionedTurnover(Some(0), Some(0), Some(0))
      result.total shouldBe periodOfAccountsTurnover
    }

    "apportion a turnover of None as None" in new ApportionedTurnoverCalculator {

      val result = apportionPeriodOfAccountsTurnover(
        AC3(LocalDate.of(2013,4,1)),
        AC4(LocalDate.of(2014,3,31)),
        CP1(LocalDate.of(2013,5,1)),
        CP2(LocalDate.of(2013,12,31)),
        AC12(None),
        AC401(None)
      )

      result shouldBe ApportionedTurnover(Some(0), Some(0), Some(0))
      result.total shouldBe 0
    }

    "apportion a negative turnover" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = -100

      val result = apportionPeriodOfAccountsTurnover(
        AC3(LocalDate.of(2013,4,1)),
        AC4(LocalDate.of(2014,3,31)),
        CP1(LocalDate.of(2013,4,1)),
        CP2(LocalDate.of(2014,3,31)),
        AC12(periodOfAccountsTurnover),
        AC401(None)
      )

      result shouldBe ApportionedTurnover(None, Some(-100), None)
      result.total shouldBe -100
    }

    "apportion turnover to periods before and after of length zero days as None rather than Some(0)" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 7

      val result = apportionPeriodOfAccountsTurnover(
        AC3(LocalDate.of(2012,1,1)),
        AC4(LocalDate.of(2012,1,1)),
        CP1(LocalDate.of(2012,1,1)),
        CP2(LocalDate.of(2012,1,1)),
        AC12(periodOfAccountsTurnover),
        AC401(None)
      )

      result shouldBe ApportionedTurnover(None, Some(7), None)
      result.total shouldBe periodOfAccountsTurnover
    }

    "throw an exception when accounting period is outside the period of accounts" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 10234

      val ac3 = AC3(LocalDate.of(2012,1,1))
      val ac4 = AC4(LocalDate.of(2012,1,2))

      intercept[IllegalStateException] {
        apportionPeriodOfAccountsTurnover(
          ac3,
          ac4,
          CP1(LocalDate.of(2011,12,31)),
          CP2(LocalDate.of(2012,1,2)),
          AC12(periodOfAccountsTurnover),
          AC401(None)
        )
      }

      intercept[IllegalStateException] {
        apportionPeriodOfAccountsTurnover(
          ac3,
          ac4,
          CP1(LocalDate.of(2011,12,31)),
          CP2(LocalDate.of(2012,1,2)),
          AC12(periodOfAccountsTurnover),
          AC401(None)
        )
      }

      intercept[IllegalStateException] {
        apportionPeriodOfAccountsTurnover(
          ac3,
          ac4,
          CP1(LocalDate.of(2012,1,1)),
          CP2(LocalDate.of(2012,1,3)),
          AC12(periodOfAccountsTurnover),
          AC401(None)
        )
      }

      intercept[IllegalStateException] {
        apportionPeriodOfAccountsTurnover(
          ac3,
          ac4,
          CP1(LocalDate.of(2011,12,31)),
          CP2(LocalDate.of(2012,1,3)),
          AC12(periodOfAccountsTurnover),
          AC401(None)
        )
      }
    }

    "apportion turnover includes non-OPW turnover and OPW turnover" in new ApportionedTurnoverCalculator {
      val periodOfAccountsTurnover = 7
      val opwTurnover = 10

      val result = apportionPeriodOfAccountsTurnover(
        AC3(LocalDate.of(2012,1,1)),
        AC4(LocalDate.of(2012,1,1)),
        CP1(LocalDate.of(2012,1,1)),
        CP2(LocalDate.of(2012,1,1)),
        AC12(periodOfAccountsTurnover),
        AC401(opwTurnover)
      )

      result shouldBe ApportionedTurnover(None, Some(periodOfAccountsTurnover + opwTurnover), None)
      result.total shouldBe periodOfAccountsTurnover + opwTurnover
    }

    "methods returning correct apportion" should {
      "for before" in new ApportionedTurnoverCalculator {
        val periodOfAccountsTurnover = 648300

        val result = turnoverApportionedBeforeAccountingPeriod(
          AC3(LocalDate.of(2013,4,1)),
          AC4(LocalDate.of(2014,3,31)),
          CP1(LocalDate.of(2013,5,1)),
          CP2(LocalDate.of(2013,12,31)),
          AC12(periodOfAccountsTurnover),
          AC401(None)
        )

        result.value shouldBe Some(53285)
      }

      "for during" in new ApportionedTurnoverCalculator {
        val periodOfAccountsTurnover = 648300

        val result = turnoverApportionedDuringAccountingPeriod(
          AC3(LocalDate.of(2013,4,1)),
          AC4(LocalDate.of(2014,3,31)),
          CP1(LocalDate.of(2013,5,1)),
          CP2(LocalDate.of(2013,12,31)),
          AC12(periodOfAccountsTurnover),
          AC401(None)
        )

        result.defaultValue shouldBe Some(435160)
      }

      "for after" in new ApportionedTurnoverCalculator {
        val periodOfAccountsTurnover = 648300

        val result = turnoverApportionedAfterAccountingPeriod(
          AC3(LocalDate.of(2013,4,1)),
          AC4(LocalDate.of(2014,3,31)),
          CP1(LocalDate.of(2013,5,1)),
          CP2(LocalDate.of(2013,12,31)),
          AC12(periodOfAccountsTurnover),
          AC401(None)
        )

        result.value shouldBe Some(159855)
      }
    }
  }
}
