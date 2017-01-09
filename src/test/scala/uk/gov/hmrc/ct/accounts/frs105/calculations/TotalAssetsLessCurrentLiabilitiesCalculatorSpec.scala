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

class TotalAssetsLessCurrentLiabilitiesCalculatorSpec extends WordSpec with Matchers with TotalAssetsLessCurrentLiabilitiesCalculator {

  "TotalAssetsLessCurrentLiabilitiesCalculator" should {

    "calculate Current Total Assets Less Current Liabilities" in {
      val ac60 = AC60(Some(50))
      val ac450 = AC450(Some(20))
      val ac460 = AC460(Some(20))
      calculateCurrentTotalAssetsLessCurrentLiabilities(ac60, ac450, ac460) shouldBe AC62(Some(90))
    }

    "return None for Current Total Assets Less Current Liabilities if all inputs are none" in {
      val ac60 = AC60(None)
      val ac450 = AC450(None)
      val ac460 = AC460(None)
      calculateCurrentTotalAssetsLessCurrentLiabilities(ac60, ac450, ac460) shouldBe AC62(None)
    }

    "calculate Previous Total Assets Less Current Liabilities" in {
      val ac61 = AC61(Some(50))
      val ac451 = AC451(Some(20))
      val ac461 = AC461(Some(20))
      calculatePreviousTotalAssetsLessCurrentLiabilities(ac61, ac451, ac461) shouldBe AC63(Some(90))
    }

    "return None for Previous Total Assets Less Current Liabilities if all inputs are none" in {
      val ac61 = AC61(None)
      val ac451 = AC451(None)
      val ac461 = AC461(None)
      calculatePreviousTotalAssetsLessCurrentLiabilities(ac61, ac451, ac461) shouldBe AC63(None)
    }

  }
}
