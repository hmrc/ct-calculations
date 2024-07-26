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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.computations.{CP79, CP81, CPAux1}

class TotalExpenditureQualifyingForTheFirstYearAllowanceCalculatorSpec extends AnyWordSpec with Matchers {

  "TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator" should {
    "calculate the total expenditure qualifying for the first year allowance with populated values" in new TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator {
      totalExpenditureQualifyingForTheFirstYearAllowance(cp79 = CP79(Some(1)),
                                                         cpAux1 = CPAux1(2)) shouldBe CP81(3)
    }

    "calculate the total expenditure qualifying for the first year allowance without values" in new TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator {
      totalExpenditureQualifyingForTheFirstYearAllowance(CP79(None), CPAux1(0)) shouldBe CP81(0)
    }
  }
}
