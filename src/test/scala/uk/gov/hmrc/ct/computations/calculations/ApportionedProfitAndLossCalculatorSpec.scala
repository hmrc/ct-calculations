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

class ApportionedProfitAndLossCalculatorSpec extends WordSpec with Matchers {

  // this filing period is 18 months long and has a leap year
  val startDate = LocalDate.parse("2012-01-01")
  val endDate = LocalDate.parse("2013-06-30")

  "Apportioned Profit and Loss Calculator" should {
    "return all profits in during period if no start and end date provided" in new ApportionedProfitAndLossCalculator {
      val result = apportionmentProfitOrLossCalculation(ApportionmentProfitOrLossParameters(startDate, endDate, 10234, None, None))
      result.value shouldBe ApportionmentProfitOrLossResult(None, Some(10234), None, startDate, endDate)
      result.value.total shouldBe 10234
    }
    "return profits in before and during periods if a start date is provided and end date is NOT provided" in new ApportionedProfitAndLossCalculator {
      val result = apportionmentProfitOrLossCalculation(ApportionmentProfitOrLossParameters(startDate, endDate, 10234, Some(LocalDate.parse("2012-07-01")), None))
      result.value shouldBe ApportionmentProfitOrLossResult(Some(3406), Some(6828), None, LocalDate.parse("2012-07-01"), endDate)
      result.value.total shouldBe 10234
    }
    "return profits in after and during periods if an end date is provided and start date is NOT provided" in new ApportionedProfitAndLossCalculator {
      val result = apportionmentProfitOrLossCalculation(ApportionmentProfitOrLossParameters(startDate, endDate, 10234, None, Some(LocalDate.parse("2012-12-31"))))
      result.value shouldBe ApportionmentProfitOrLossResult(None, Some(6847), Some(3387), startDate, LocalDate.parse("2012-12-31"))
      result.value.total shouldBe 10234
    }
    "return profits before, after and during periods if start and end dates provided, round down the during" in new ApportionedProfitAndLossCalculator {
      val result = apportionmentProfitOrLossCalculation(ApportionmentProfitOrLossParameters(startDate, endDate, 10234, Some(LocalDate.parse("2012-04-01")), Some(LocalDate.parse("2013-03-31"))))
      result.value shouldBe ApportionmentProfitOrLossResult(Some(1703), Some(6828), Some(1703), LocalDate.parse("2012-04-01"), LocalDate.parse("2013-03-31"))
      result.value.total shouldBe 10234
    }
    "return profits before, after and during periods if start and end dates provided 10233, round down the during and balance to previous period" in new ApportionedProfitAndLossCalculator {
      val result = apportionmentProfitOrLossCalculation(ApportionmentProfitOrLossParameters(startDate, endDate, 10233, Some(LocalDate.parse("2012-04-01")), Some(LocalDate.parse("2013-03-31"))))
      result.value shouldBe ApportionmentProfitOrLossResult(Some(1703), Some(6828), Some(1702), LocalDate.parse("2012-04-01"), LocalDate.parse("2013-03-31"))
      result.value.total shouldBe 10233
    }

    "return loss before, after and during periods if start and end dates provided 10233, round down the during and balance to previous period" in new ApportionedProfitAndLossCalculator {
      val result = apportionmentProfitOrLossCalculation(ApportionmentProfitOrLossParameters(startDate, endDate, -10233, Some(LocalDate.parse("2012-04-01")), Some(LocalDate.parse("2013-03-31"))))
      result.value shouldBe ApportionmentProfitOrLossResult(Some(-1703), Some(-6828), Some(-1702), LocalDate.parse("2012-04-01"), LocalDate.parse("2013-03-31"))
      result.value.total shouldBe -10233
    }

    "return profit before, after and during periods if start and end dates provided only 1 day apart, round down the during" in new ApportionedProfitAndLossCalculator {
      val result = apportionmentProfitOrLossCalculation(ApportionmentProfitOrLossParameters(startDate, endDate, 10234, Some(LocalDate.parse("2012-04-01")), Some(LocalDate.parse("2012-04-02"))))
      result.value shouldBe ApportionmentProfitOrLossResult(Some(1703), Some(37), Some(8494), LocalDate.parse("2012-04-01"), LocalDate.parse("2012-04-02"))
      result.value.total shouldBe 10234
    }

    "Calculate values for Input AP 1 May 2013 to 31 December 2013 and CH 1 April 2013 to 31 March 2014" in new ApportionedProfitAndLossCalculator {
      val result = apportionmentProfitOrLossCalculation(ApportionmentProfitOrLossParameters(LocalDate.parse("2013-04-01"), LocalDate.parse("2014-03-31"), 648300, Some(LocalDate.parse("2013-05-01")), Some(LocalDate.parse("2013-12-31"))))
      result.value shouldBe ApportionmentProfitOrLossResult(Some(53285), Some(435160), Some(159855), LocalDate.parse("2013-05-01"), LocalDate.parse("2013-12-31"))
      result.value.total shouldBe 648300
    }

    "Calculate values with a profit and loss of zero returns Some(0) NOT None" in new ApportionedProfitAndLossCalculator {
      val result = apportionmentProfitOrLossCalculation(ApportionmentProfitOrLossParameters(LocalDate.parse("2013-04-01"), LocalDate.parse("2014-03-31"), 0, Some(LocalDate.parse("2013-05-01")), Some(LocalDate.parse("2013-12-31"))))
      result.value shouldBe ApportionmentProfitOrLossResult(Some(0), Some(0), Some(0), LocalDate.parse("2013-05-01"), LocalDate.parse("2013-12-31"))
      result.value.total shouldBe 0
    }
  }
}
