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

package uk.gov.hmrc.ct.computations.offPayRollWorking

import org.joda.time.LocalDate
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.box.CtValidation

class offPayRollWorkingSpec extends AnyWordSpec with Matchers {

  "isOPWEnabled" should {
    "return false for before 2017-04-05" in {
      isOPWEnabled(new LocalDate("2017-04-04")) shouldBe false
    }

    "return false for 2017-04-05" in {
      isOPWEnabled(new LocalDate("2017-04-05")) shouldBe false
    }

    "return true for before 2017-04-05" in {
      isOPWEnabled(new LocalDate("2017-04-06")) shouldBe true
    }
  }


  "DeductionCannotBeGreaterThanProfit" should {
    "return no errors if loss is less than profit" in {
      DeductionCannotBeGreaterThanProfit(AC401(2), AC403(1)) shouldBe Set.empty
    }

    "return no errors if loss the same profit" in {
      DeductionCannotBeGreaterThanProfit(AC401(2), AC403(2)) shouldBe Set.empty
    }

    "return an error if loss is more than profit" in {
      DeductionCannotBeGreaterThanProfit(AC401(2), AC403(3)) shouldBe Set(CtValidation(Some("AC403"), "error.AC403.exceeds.AC401"))
    }
  }

}
