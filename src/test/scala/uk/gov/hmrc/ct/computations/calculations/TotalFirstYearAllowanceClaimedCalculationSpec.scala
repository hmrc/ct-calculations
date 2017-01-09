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
import uk.gov.hmrc.ct.computations.{CP85, CP86, CP87}

class TotalFirstYearAllowanceClaimedCalculationSpec extends WordSpec with Matchers {

  "TotalFirstYearAllowanceClaimedCalculation" should {
    "return an option with the calculation value" in new TotalFirstYearAllowanceClaimedCalculation {
      totalFirstYearAllowanceClaimedCalculation(cp85 = CP85(Some(1)), cp86 = CP86(Some(2))) should be (CP87(3))
    }
  }

}
