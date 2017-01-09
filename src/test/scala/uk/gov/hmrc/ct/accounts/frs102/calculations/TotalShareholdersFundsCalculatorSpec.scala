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

class TotalShareholdersFundsCalculatorSpec extends WordSpec with Matchers with TotalShareholdersFundsCalculator {

  "TotalShareholdersFundsCalculator" should {

    "calculate Current Total Shareholders Funds" in {
      val ac70 = AC70(Some(50))
      val ac76 = AC76(Some(20))
      val ac74 = AC74(Some(30))
      calculateCurrentTotalShareholdersFunds(ac70, ac76, ac74) shouldBe AC80(Some(100))
    }

    "calculate Current Total Shareholders Funds with an empty field" in {
      val ac70 = AC70(Some(50))
      val ac76 = AC76(None)
      val ac74 = AC74(Some(30))
      calculateCurrentTotalShareholdersFunds(ac70, ac76, ac74) shouldBe AC80(Some(80))
    }

    "return None for Current Total Shareholders Funds if all inputs are None" in {
      val ac70 = AC70(None)
      val ac76 = AC76(None)
      val ac74 = AC74(None)
      calculateCurrentTotalShareholdersFunds(ac70, ac76, ac74) shouldBe AC80(None)
    }

    "calculate Previous Total Shareholders Funds" in {
      val ac71 = AC71(Some(50))
      val ac77 = AC77(Some(20))
      val ac75 = AC75(Some(30))
      calculatePreviousTotalShareholdersFunds(ac71, ac77, ac75) shouldBe AC81(Some(100))
    }
    "calculate Previous Total Shareholders Funds with an empty field" in {
      val ac71 = AC71(None)
      val ac77 = AC77(Some(20))
      val ac75 = AC75(Some(30))
      calculatePreviousTotalShareholdersFunds(ac71, ac77, ac75) shouldBe AC81(Some(50))
    }

    "return None for Previous Total Shareholders Funds if all inputs are None" in {
      val ac71 = AC71(None)
      val ac77 = AC77(None)
      val ac75 = AC75(None)
      calculatePreviousTotalShareholdersFunds(ac71, ac77, ac75) shouldBe AC81(None)
    }

  }

}
