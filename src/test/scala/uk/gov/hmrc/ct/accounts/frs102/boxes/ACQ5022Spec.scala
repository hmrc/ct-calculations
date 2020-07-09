/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.BoxesFixture
import uk.gov.hmrc.ct.box.CtValidation


class ACQ5022Spec extends WordSpec with Matchers with BoxesFixture {

  "ACQ5022" should {

    "for Full Accounts pass validation" when {

      "all no value" in {
        ac42noValue
        ac43noValue
        acq5021noValue

        ACQ5022(None).validate(boxRetriever) shouldBe empty
      }
    }

    "for Full Accounts fail validation" when {

      val cannotExist = Set(CtValidation(Some("ACQ5022"),"error.ACQ5022.cannot.exist",None))

      "ac42, ac43 has no value and acq5022 has value" in {
        ac42noValue
        ac43noValue
        acq5021noValue

        ACQ5022(Some(false)).validate(boxRetriever) shouldBe cannotExist
      }
    }
  }
}
