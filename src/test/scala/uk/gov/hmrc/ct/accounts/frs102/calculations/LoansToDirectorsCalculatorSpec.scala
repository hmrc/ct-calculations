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
import uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors.{AC306A, AC307A, AC308A, AC309A}

class LoansToDirectorsCalculatorSpec extends WordSpec with Matchers {

  "LoansToDirectorsCalculator" should {
    "calculating AC309A" when {
      "return None only if all inputs are empty" in new LoansToDirectorsCalculator {
        calculateLoanBalanceAtEndOfPOA(AC306A(None), AC307A(None), AC308A(None)) shouldBe AC309A(None)
        calculateLoanBalanceAtEndOfPOA(AC306A(Some(0)), AC307A(None), AC308A(None)) shouldBe AC309A(Some(0))
        calculateLoanBalanceAtEndOfPOA(AC306A(None), AC307A(Some(0)), AC308A(None)) shouldBe AC309A(Some(0))
        calculateLoanBalanceAtEndOfPOA(AC306A(None), AC307A(None), AC308A(Some(0))) shouldBe AC309A(Some(0))
      }

      "return zero if net value is 0" in new LoansToDirectorsCalculator {
        calculateLoanBalanceAtEndOfPOA(AC306A(Some(0)), AC307A(Some(0)), AC308A(Some(0))) shouldBe AC309A(Some(0))
      }

      "return correct positive value" in new LoansToDirectorsCalculator {
        calculateLoanBalanceAtEndOfPOA(AC306A(Some(11)), AC307A(Some(13)), AC308A(Some(7))) shouldBe AC309A(Some(17))
      }

      "return correct negative value" in new LoansToDirectorsCalculator {
        calculateLoanBalanceAtEndOfPOA(AC306A(Some(11)), AC307A(Some(13)), AC308A(Some(27))) shouldBe AC309A(Some(-3))
      }
    }
  }
}
