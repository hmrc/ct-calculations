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


class ACQ5021Spec extends WordSpec with Matchers with BoxesFixture {

  "ACQ5021" should {

    "for Full Accounts pass validation" when {

      "ac42, acq5021 have value" in {
        ac42withValue
        ac43noValue
        acq5022noValue

        ACQ5021(Some(false)).validate(boxRetriever) shouldBe empty
      }

      "ac42, acq5022 have value" in {
        ac42withValue
        ac43noValue
        acq5022withValue

        ACQ5021(None).validate(boxRetriever) shouldBe empty
      }

      "ac43, acq5021 have value" in {
        ac42noValue
        ac43withValue
        acq5022noValue

        ACQ5021(Some(false)).validate(boxRetriever) shouldBe empty
      }

      "ac43, acq5022 have value" in {
        ac42noValue
        ac43withValue
        acq5022withValue

        ACQ5021(None).validate(boxRetriever) shouldBe empty
      }

      "ac42, acq5022, acq5021 have value" in {
        ac42withValue
        ac43noValue
        acq5022withValue

        ACQ5021(Some(true)).validate(boxRetriever) shouldBe empty
      }

      "ac43, acq5022, acq5021 have value" in {
        ac42noValue
        ac43withValue
        acq5022withValue

        ACQ5021(Some(true)).validate(boxRetriever) shouldBe empty
      }

      "ac42, ac43, acq5022, acq5021 have value" in {
        ac42withValue
        ac43withValue
        acq5022withValue

        ACQ5021(Some(true)).validate(boxRetriever) shouldBe empty
      }

      "all no value" in {
        ac42noValue
        ac43noValue
        acq5022noValue

        ACQ5021(None).validate(boxRetriever) shouldBe empty
      }
    }

    "for Full Accounts fail validation" when {

      val errorAtLeastOne = Set(CtValidation(None,"error.balance.sheet.intangible.assets.one.box.required",None))
      val cannotExist = Set(CtValidation(Some("ACQ5021"),"error.ACQ5021.cannot.exist",None))

      "ac42 has value and acq5021,acq5022 have no value" in {
        ac42withValue
        ac43noValue
        acq5022noValue

        ACQ5021(None).validate(boxRetriever) shouldBe errorAtLeastOne
      }

      "ac43 has value and acq5021,acq5022 have no value" in {
        ac42noValue
        ac43withValue
        acq5022noValue

        ACQ5021(None).validate(boxRetriever) shouldBe errorAtLeastOne
      }

      "ac42,ac43 have value and acq5021,acq5022 have no value" in {
        ac42withValue
        ac43withValue
        acq5022noValue

        ACQ5021(None).validate(boxRetriever) shouldBe errorAtLeastOne
      }

      "ac42, ac43 has no value and acq5021 has value" in {
        ac42noValue
        ac43noValue
        acq5022noValue

        ACQ5021(Some(false)).validate(boxRetriever) shouldBe cannotExist
      }

    }
  }
}
