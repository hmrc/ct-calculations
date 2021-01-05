/*
 * Copyright 2021 HM Revenue & Customs
 *
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
