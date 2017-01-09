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


class ACQ5031Spec extends WordSpec with Matchers with BoxesFixture {

  "ACQ5031" should {

    "for Full Accounts pass validation" when {

      "ac44, acq5031 have value" in {
        ac44withValue
        ac45noValue
        acq5032noValue
        acq5033noValue
        acq5034noValue
        acq5035noValue

        ACQ5031(Some(false)).validate(boxRetriever) shouldBe empty
      }

      "ac44, acq5032 have value" in {
        ac44withValue
        ac45noValue
        acq5032withValue
        acq5033noValue
        acq5034noValue
        acq5035noValue

        ACQ5031(None).validate(boxRetriever) shouldBe empty
      }

      "ac44, acq5033 have value" in {
        ac44withValue
        ac45noValue
        acq5032noValue
        acq5033withValue
        acq5034noValue
        acq5035noValue

        ACQ5031(None).validate(boxRetriever) shouldBe empty
      }

      "ac44, acq5034 have value" in {
        ac44withValue
        ac45noValue
        acq5032noValue
        acq5033noValue
        acq5034withValue
        acq5035noValue

        ACQ5031(None).validate(boxRetriever) shouldBe empty
      }

      "ac44, acq5035 have value" in {
        ac44withValue
        ac45noValue
        acq5032noValue
        acq5033noValue
        acq5034noValue
        acq5035withValue

        ACQ5031(None).validate(boxRetriever) shouldBe empty
      }

      "ac45, acq5031 have value" in {
        ac44noValue
        ac45withValue
        acq5032noValue
        acq5033noValue
        acq5034noValue
        acq5035noValue

        ACQ5031(Some(true)).validate(boxRetriever) shouldBe empty
      }

      "ac45, acq5032 have value" in {
        ac44noValue
        ac45withValue
        acq5032withValue
        acq5033noValue
        acq5034noValue
        acq5035noValue

        ACQ5031(None).validate(boxRetriever) shouldBe empty
      }

      "ac45, acq5033 have value" in {
        ac44noValue
        ac45withValue
        acq5032noValue
        acq5033withValue
        acq5034noValue
        acq5035noValue

        ACQ5031(None).validate(boxRetriever) shouldBe empty
      }

      "ac45, acq5034 have value" in {
        ac44noValue
        ac45withValue
        acq5032noValue
        acq5033noValue
        acq5034withValue
        acq5035noValue

        ACQ5031(None).validate(boxRetriever) shouldBe empty
      }

      "ac45, acq5035 have value" in {
        ac44noValue
        ac45withValue
        acq5032noValue
        acq5033noValue
        acq5034noValue
        acq5035withValue

        ACQ5031(None).validate(boxRetriever) shouldBe empty
      }

      "ac45, acq5033, acq5035 have value" in {
        ac44noValue
        ac45withValue
        acq5032noValue
        acq5033withValue
        acq5034noValue
        acq5035withValue

        ACQ5031(None).validate(boxRetriever) shouldBe empty
      }

      "all no value" in {
        ac44noValue
        ac45noValue
        acq5032noValue
        acq5033noValue
        acq5034noValue
        acq5035noValue

        ACQ5031(None).validate(boxRetriever) shouldBe empty
      }
    }

    "for Full Accounts fail validation" when {

      val atLeastOneError = Set(CtValidation(None,"error.balance.sheet.tangible.assets.one.box.required",None))
      val cannotExistError = Set(CtValidation(Some("ACQ5031"),"error.ACQ5031.cannot.exist",None))

      "ac44 has value and other boxes have no value" in {
        ac44withValue
        ac45noValue
        acq5032noValue
        acq5033noValue
        acq5034noValue
        acq5035noValue

        ACQ5031(None).validate(boxRetriever) shouldBe atLeastOneError
      }

      "ac45 has value and other boxes have no value" in {
        ac44noValue
        ac45withValue
        acq5032noValue
        acq5033noValue
        acq5034noValue
        acq5035noValue

        ACQ5031(None).validate(boxRetriever) shouldBe atLeastOneError
      }

      "ac44,ac45 have value and other boxes have no value" in {
        ac44noValue
        ac45withValue
        acq5032noValue
        acq5033noValue
        acq5034noValue
        acq5035noValue

        ACQ5031(None).validate(boxRetriever) shouldBe atLeastOneError
      }

      "ac44,ac45 have no value and acq5031 has value" in {
        ac44noValue
        ac45noValue
        acq5032noValue
        acq5033noValue
        acq5034noValue
        acq5035noValue

        ACQ5031(Some(false)).validate(boxRetriever) shouldBe cannotExistError
      }

    }
  }
}
