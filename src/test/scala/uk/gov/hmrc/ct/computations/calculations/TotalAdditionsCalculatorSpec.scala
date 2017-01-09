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

class TotalAdditionsCalculatorSpec extends WordSpec with Matchers {
  "Total Additions Calculator" should {
    "calculate additions with populated values" in new TotalAdditionsCalculator {
      totalAdditionsCalculation(cp46 = CP46(Some(2)),
                                cp47 = CP47(Some(3)),
                                cp48 = CP48(Some(4)),
                                cp49 = CP49(Some(5)),
                                cp51 = CP51(Some(6)),
                                cp52 = CP52(Some(7)),
                                cp53 = CP53(Some(8))) shouldBe CP54(35)
    }
  }
}
