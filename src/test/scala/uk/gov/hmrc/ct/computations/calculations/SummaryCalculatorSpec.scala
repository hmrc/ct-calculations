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

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._
import org.scalatest.prop.TableDrivenPropertyChecks._

class SummaryCalculatorSpec extends WordSpec with Matchers {

  "Calculating TradingLossesBroughtForwardForSummary (CP257)" should {
    "return None if CP238 and CP283b are both None" in new SummaryCalculator {
      calculateTradingLossesBroughtForwardForSummary(CP238(None), CP283b(None)) shouldBe CP257(None)
    }
    "return CP238 if CP283b is None" in new SummaryCalculator {
      calculateTradingLossesBroughtForwardForSummary(CP238(Some(20)), CP283b(None)) shouldBe CP257(Some(20))
    }
    "return CP238 - CP283b is Some" in new SummaryCalculator {
      calculateTradingLossesBroughtForwardForSummary(CP238(Some(20)), CP283b(Some(10))) shouldBe CP257(Some(10))
    }
  }
  "Calculating net trading and professional profits (CP258)" should {
    "return CP256 - CP257 when CP257 is defined" in new SummaryCalculator {
      val cp256 = CP256(1000)
      val cp257 = CP257(Some(300))
      calculateNetTradingAndProfessionalProfits(cp256, cp257) shouldBe CP258(700)
    }
    "return CP256 - 0 when CP257 is not defined" in new SummaryCalculator {
      val cp256 = CP256(1000)
      val cp257 = CP257(None)
      calculateNetTradingAndProfessionalProfits(cp256, cp257) shouldBe CP258(1000)
    }
  }

  "Calculating profits and gains from non trading loan relationships (CP259)" should {
    "populate with CP43 when CP43 is defined" in new SummaryCalculator {
      val cp43 = CP43(Some(43))
      calculateProfitsAndGainsFromNonTradingLoanRelationships(cp43) shouldBe CP259(43)
    }
    "populate with zero when CP43 is not define" in new SummaryCalculator {
      val cp43 = CP43(None)
      calculateProfitsAndGainsFromNonTradingLoanRelationships(cp43) shouldBe CP259(0)
    }
  }

  val CP99Table = Table(
    ("cp186", "cp668", "cp674", "cp91", "cp670", "cp99"),
    (Some(50), Some(40), None, Some(0), Some(0), 90),
    (Some(15), Some(3), None, Some(45), Some(0), 0),
    (Some(12), None, Some(55), Some(0), Some(0), 67),
    (Some(100), None, Some(10), Some(45), Some(0), 65)
  )

  "Calculating trade net allowances (CP99)" should {
    "pass acceptance criteria from CATO-2605" in new SummaryCalculator {
      forAll(CP99Table) {
        (cp186: Option[Int], cp668: Option[Int], cp674: Option[Int], cp91: Option[Int], cp670: Option[Int], cp99: Int) => {
          calculateTradeNetAllowancesForSummary(CP186(cp186), CP668(cp668), CP674(cp674), CP91(cp91), CP670(cp670)) shouldBe CP99(cp99)
        }
      }
    }
  }
}
