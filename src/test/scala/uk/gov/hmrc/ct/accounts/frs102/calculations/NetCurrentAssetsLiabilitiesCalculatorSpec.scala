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

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class NetCurrentAssetsLiabilitiesCalculatorSpec extends WordSpec with Matchers with NetCurrentAssetsLiabilitiesCalculator {

  "NetCurrentAssetsLiabilitiesCalculator" should {

    "calculate Current Net Current Assets Liabilities" in {
      calculateCurrentNetCurrentAssetsLiabilities(AC56(Some(50)), AC138B(Some(20)), AC58(Some(30))) shouldBe AC60(Some(40))
    }

    "return None for Current Net Current Assets Liabilities when all inputs are None" in {
      calculateCurrentNetCurrentAssetsLiabilities(AC56(None), AC138B(None), AC58(None)) shouldBe AC60(None)
    }

    "calculate Previous Net Current Assets Liabilities" in {
      calculatePreviousNetCurrentAssetsLiabilities(AC57(Some(50)), AC139B(Some(20)), AC59(Some(30))) shouldBe AC61(Some(40))
    }

    "return None for Previous Net Current Assets Liabilities when all inputs are None" in {
      calculatePreviousNetCurrentAssetsLiabilities(AC57(None), AC139B(None), AC59(None)) shouldBe AC61(None)
    }

  }

}
