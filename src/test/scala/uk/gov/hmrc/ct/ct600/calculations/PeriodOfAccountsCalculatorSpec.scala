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

package uk.gov.hmrc.ct.ct600.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AC3, AC4}

class PeriodOfAccountsCalculatorSpec extends WordSpec with Matchers with PeriodOfAccountsCalculator {

  "isLongPeriodOfAccounts" should {

    "return true for a PoA of 15 months" in {
      val poaStartDate = AC3(new LocalDate(2015, 4, 1))
      val poaEndDate = AC4(new LocalDate(2016, 7, 1))

      isLongPeriodOfAccounts(poaStartDate, poaEndDate) shouldBe true
    }

    "return true for a PoA of 12 months" in {
      val poaStartDate = AC3(new LocalDate(2015, 4, 1))
      val poaEndDate = AC4(new LocalDate(2016, 4, 1))

      isLongPeriodOfAccounts(poaStartDate, poaEndDate) shouldBe true
    }

    "return false for a PoA of 12 months minus one day" in {
      val poaStartDate = AC3(new LocalDate(2015, 4, 1))
      val poaEndDate = AC4(new LocalDate(2016, 3, 31))

      isLongPeriodOfAccounts(poaStartDate, poaEndDate) shouldBe false
    }

  }

}
