/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.BoxesFixture
import uk.gov.hmrc.ct.box.CtValidation


class ACQ5035Spec extends WordSpec with Matchers with BoxesFixture {

  "ACQ5035" should {

    "for Full Accounts pass validation" when {

      "all no value" in {
        ac44noValue
        ac45noValue
        acq5031noValue
        acq5032noValue
        acq5033noValue
        acq5034noValue

        ACQ5035(None).validate(boxRetriever) shouldBe empty
      }
    }

    "for Full Accounts fail validation" when {

      val cannotExistError = Set(CtValidation(Some("ACQ5035"),"error.ACQ5035.cannot.exist",None))

      "ac44,ac45 have no value and acq5035 has value" in {
        ac44noValue
        ac45noValue
        acq5031noValue
        acq5032noValue
        acq5033noValue
        acq5034noValue

        ACQ5035(Some(false)).validate(boxRetriever) shouldBe cannotExistError
      }

    }
  }
}
