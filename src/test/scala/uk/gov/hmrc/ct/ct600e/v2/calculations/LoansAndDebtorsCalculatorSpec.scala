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

package uk.gov.hmrc.ct.ct600e.v2.calculations

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.ct600e.v2._

class LoansAndDebtorsCalculatorSpec extends AnyWordSpec with Matchers with LoansAndDebtorsCalculator {

  "LoansAndDebtorsCalculator" should {
    "return None if all income boxes are None" in {
      calculateFieldValue(E24eA(None), E24eB(None)) shouldBe E24e(None)
    }

    "return sum of populated boxes if some income boxes have values" in {
      calculateFieldValue(E24eA(None), E24eB(Some(31))) shouldBe E24e(Some(31))
    }

    "return sum of all boxes if all income boxes have values" in {
      calculateFieldValue(E24eA(Some(12)), E24eB(Some(10))) shouldBe E24e(Some(22))
    }
  }
}
