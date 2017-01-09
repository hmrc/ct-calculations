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

class TotalNetAssetsLiabilitiesCalculatorSpec extends WordSpec with Matchers with TotalNetAssetsLiabilitiesCalculator {

  "TotalNetAssetsLiabilitiesCalculator" should {

    "calculate current total Net Assets and Liabilities" in {
      val ac62 = AC62(Some(50))
      val ac64 = AC64(Some(20))
      val ac66 = AC66(Some(20))
      val ac470 = AC470(Some(20))
      calculateCurrentTotalNetAssetsLiabilities(ac62, ac64, ac66, ac470) shouldBe AC68(Some(-10))
    }

    "return None for current total Net Assets and Liabilities when inputs are None" in {
      val ac62 = AC62(None)
      val ac64 = AC64(None)
      val ac66 = AC66(None)
      val ac470 = AC470(None)
      calculateCurrentTotalNetAssetsLiabilities(ac62, ac64, ac66, ac470) shouldBe AC68(None)
    }

    "calculate previous total Net Assets and Liabilities" in {
      val ac63 = AC63(Some(50))
      val ac65 = AC65(Some(20))
      val ac67 = AC67(Some(20))
      val ac471 = AC471(Some(20))
      calculatePreviousTotalNetAssetsLiabilities(ac63, ac65, ac67, ac471) shouldBe AC69(Some(-10))
    }

    "return None for previous total Net Assets and Liabilities when inputs are None" in {
      val ac63 = AC63(None)
      val ac65 = AC65(None)
      val ac67 = AC67(None)
      val ac471 = AC471(None)
      calculatePreviousTotalNetAssetsLiabilities(ac63, ac65, ac67, ac471) shouldBe AC69(None)
    }
  }

}
