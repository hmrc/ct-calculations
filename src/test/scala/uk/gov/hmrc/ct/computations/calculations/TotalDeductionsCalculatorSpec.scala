/*
 * Copyright 2023 HM Revenue & Customs
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

class TotalDeductionsCalculatorSpec extends WordSpec with Matchers {

  "Total Deductions Calculator" should {
    "calculate deductions with populated values" in new TotalDeductionsCalculator {
      totalDeductionsCalculation(cp55 = CP55(Some(1)),
                                 cp57 = CP57(Some(2)),
                                 cp58 = CP58(3),
                                 cp507 = CP507(4),
                                 cp505 = CP505(Some(5)),
                                 cp983 = CP983(10)) shouldBe CP59(25)
    }
  }
}
