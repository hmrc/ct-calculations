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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.BoxesFixture
import uk.gov.hmrc.ct.box.CtValidation


class ACQ5033Spec extends WordSpec with Matchers with BoxesFixture {

  "ACQ5033" should {

    "for Full Accounts pass validation" when {

      "all no value" in {
        ac44noValue
        ac45noValue
        acq5031noValue
        acq5032noValue
        acq5034noValue
        acq5035noValue

        ACQ5033(None).validate(boxRetriever) shouldBe empty
      }
    }

    "for Full Accounts fail validation" when {

      val cannotExistError = Set(CtValidation(Some("ACQ5033"),"error.ACQ5033.cannot.exist",None))

      "ac44,ac45 have no value and acq5033 has value" in {
        ac44noValue
        ac45noValue
        acq5031noValue
        acq5032noValue
        acq5034noValue
        acq5035noValue

        ACQ5033(Some(false)).validate(boxRetriever) shouldBe cannotExistError
      }

    }
  }
}
