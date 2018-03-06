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
import uk.gov.hmrc.ct.ct600.v3._

class B325CalculatorSpec extends WordSpec with Matchers {

  "B325" should{

    "be same as B335 if B335 <= B315 for the single financial year" in new B325Calculator {
      {
        calculateB325(B335(100), B385(0), B315(100), B330(2017), B380(None)) shouldBe B325(Some(100))
      }
    }

    "be same as total figure of (B335 + B385) if (B335 + B385) <= B315 for the two financial year" in new B325Calculator {
      {
        calculateB325(B335(200), B385(200), B315(400), B330(2017), B380(Some(2018))) shouldBe B325(Some(400))
      }
    }
    "be 0 when value of B335 exceed the value in B315 for the single financial year" in new B325Calculator {
      {
        calculateB325(B335(10), B385(0), B315(5), B330(2017), B380(None)) shouldBe B325(Some(0))
      }
    }

    "be 0 when value of (B335 + B385) exceed the value in B315 for the two financial year" in new B325Calculator {
      {
        calculateB325(B335(20), B385(20), B315(15), B330(2017), B380(Some(2018))) shouldBe B325(Some(0))
      }
    }
  }
}
