/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v3.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.CP117
import uk.gov.hmrc.ct.ct600.v3.{B315, B325}

class B325CalculatorSpec extends WordSpec with Matchers {
  "B325" should {
    "be same as CP117 if CP117 == B315" in new B325Calculator {
      {
        calculateB325(CP117(123), B315(123)) shouldBe B325(123)
      }
    }
    "be 0 when CP117 != B315" in new B325Calculator {
      {
        calculateB325(CP117(123), B315(12)) shouldBe B325(0)
      }
    }
  }
}
