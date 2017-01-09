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

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class TotalCreditorsWithinOneYearCalculatorSpec extends WordSpec with Matchers with TotalCreditorsWithinOneYearCalculator {

  "calculateCurrentTotalCreditorsWithinOneYear" should {

    "have result None if no inputs are populated" in {
      calculateCurrentTotalCreditorsWithinOneYear(AC142(None), AC144(None), AC146(None), AC148(None), AC150(None), AC152(None)) shouldBe AC154(None)
    }

    "have result of adding all values if all inputs are populated" in {
      calculateCurrentTotalCreditorsWithinOneYear(AC142(Some(10)), AC144(Some(20)), AC146(Some(30)), AC148(Some(40)), AC150(Some(50)), AC152(Some(60))) shouldBe AC154(Some(210))
    }

    "have result of adding all values if some inputs are populated" in {
      calculateCurrentTotalCreditorsWithinOneYear(AC142(Some(10)), AC144(Some(20)), AC146(None), AC148(Some(40)), AC150(Some(50)), AC152(None)) shouldBe AC154(Some(120))
    }

  }

  "calculatePreviousTotalCreditorsWithinOneYear" should {

    "have result None if no inputs are populated" in {
      calculatePreviousTotalCreditorsWithinOneYear(AC143(None), AC145(None), AC147(None), AC149(None), AC151(None), AC153(None)) shouldBe AC155(None)
    }

    "have result of adding all values if all inputs are populated" in {
      calculatePreviousTotalCreditorsWithinOneYear(AC143(Some(10)), AC145(Some(20)), AC147(Some(30)), AC149(Some(40)), AC151(Some(50)), AC153(Some(60))) shouldBe AC155(Some(210))
    }

    "have result of adding all values if some inputs are populated" in {
      calculatePreviousTotalCreditorsWithinOneYear(AC143(Some(10)), AC145(Some(20)), AC147(None), AC149(Some(40)), AC151(Some(50)), AC153(None)) shouldBe AC155(Some(120))
    }

  }

}
