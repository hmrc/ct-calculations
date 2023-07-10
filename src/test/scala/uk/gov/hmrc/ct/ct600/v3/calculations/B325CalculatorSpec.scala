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

package uk.gov.hmrc.ct.ct600.v3.calculations

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.computations.CP291
import uk.gov.hmrc.ct.ct600.v3._

class B325CalculatorSpec extends AnyWordSpec with Matchers {

  "B325" should{

    "be same as B350 if B350 <= B400 for the single financial year" in new B325Calculator {
      {
        calculateB325(B350(Some(100)), B400(Some(0)), B330(2017), B380(None), B315(123)) shouldBe B325(Some(100))
      }
    }

    "be same as total figure of (B350 + B400) if (B350 + B385) <= B315 for the two financial year" in new B325Calculator {
      {
        calculateB325(B350(Some(200)), B400(Some(200)), B330(2017), B380(Some(2018)),B315(500)) shouldBe B325(Some(400))
      }
    }
    "be 0 when value of B350 exceed the value in B315 for the single financial year" in new B325Calculator {
      {
        calculateB325(B350(Some(10)), B400(Some(0)), B330(2017), B380(None),B315(5)) shouldBe B325(Some(0))
      }
    }

    "be 0 when value of (B350 + B400) exceed the value in B315 for the two financial year" in new B325Calculator {
      {
        calculateB325(B350(Some(20)), B400(Some(20)), B330(2017), B380(Some(2018)),B315(15)) shouldBe B325(Some(0))
      }
    }

  }
}
