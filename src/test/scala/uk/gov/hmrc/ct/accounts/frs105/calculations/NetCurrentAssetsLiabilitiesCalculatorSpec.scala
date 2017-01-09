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

package uk.gov.hmrc.ct.accounts.frs105.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs105.boxes._

class NetCurrentAssetsLiabilitiesCalculatorSpec extends WordSpec with Matchers with NetCurrentAssetsLiabilitiesCalculator {

  "NetCurrentAssetsLiabilitiesCalculator" should {

    "calculate Current Net Current Assets Liabilities" in {
      val ac455 = AC455(Some(50))
      val ac465 = AC465(Some(20))
      val ac58 = AC58(Some(30))
      calculateCurrentNetCurrentAssetsLiabilities(ac455, ac465, ac58) shouldBe AC60(Some(40))
    }

    "return None for Current Net Current Assets Liabilities when all inputs are None" in {
      val ac455 = AC455(None)
      val ac465 = AC465(None)
      val ac58 = AC58(None)
      calculateCurrentNetCurrentAssetsLiabilities(ac455, ac465, ac58) shouldBe AC60(None)
    }

    "calculate Previous Net Current Assets Liabilities" in {
      val ac456 = AC456(Some(50))
      val ac466 = AC466(Some(20))
      val ac59 = AC59(Some(30))
      calculatePreviousNetCurrentAssetsLiabilities(ac456, ac466, ac59) shouldBe AC61(Some(40))
    }

    "return None for Previous Net Current Assets Liabilities when all inputs are None" in {
      val ac456 = AC456(None)
      val ac466 = AC466(None)
      val ac59 = AC59(None)
      calculatePreviousNetCurrentAssetsLiabilities(ac456, ac466, ac59) shouldBe AC61(None)
    }

  }

}
