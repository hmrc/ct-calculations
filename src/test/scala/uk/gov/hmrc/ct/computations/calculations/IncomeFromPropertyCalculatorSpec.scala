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

class IncomeFromPropertyCalculatorSpec extends WordSpec with Matchers  {

  "Income from Property calculator" should {
    "calculate net income" in new IncomeFromPropertyCalculator {
      netIncomeFromProperty(cp507 = CP507(0), cp508 = CP508(0)) shouldBe CP509(0)
    }
    "calculate negative net income" in new IncomeFromPropertyCalculator {
      netIncomeFromProperty(cp507 = CP507(0), cp508 = CP508(123)) shouldBe CP509(-123)
    }
    "calculate positive net income" in new IncomeFromPropertyCalculator {
      netIncomeFromProperty(cp507 = CP507(1000), cp508 = CP508(100)) shouldBe CP509(900)
    }
    "calculate total income" in new IncomeFromPropertyCalculator {
      totalIncomeFromProperty(CP509(0), CP510(Some(0))) shouldBe CP511(0)
    }
    "calculate negative total income" in new IncomeFromPropertyCalculator {
      totalIncomeFromProperty(CP509(-123), CP510(Some(123))) shouldBe CP511(0)
    }
    "calculate positive total income" in new IncomeFromPropertyCalculator {
      totalIncomeFromProperty(CP509(900), CP510(Some(300))) shouldBe CP511(1200)
    }
  }
}
