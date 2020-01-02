/*
 * Copyright 2020 HM Revenue & Customs
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

class B586CalculatorSpec extends WordSpec with Matchers {

  "B586" should{

    "be same as B345 for the single financial year" in new B586Calculator {
      {
        calculateB586(B360(Some(200)), B410(Some(0)), B330(2017), B380(None)) shouldBe B586(Some(200))
      }
    }

    "be same as total figure of (B345 + B395) for the two financial year" in new B586Calculator {
      {
        calculateB586(B360(Some(400)), B410(Some(400)), B330(2017), B380(Some(2018))) shouldBe B586(Some(800))
      }
    }
  }
}
