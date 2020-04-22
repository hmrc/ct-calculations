/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.boxes.AC8033
import uk.gov.hmrc.ct.box.CtValidation

class AC8033Spec extends WordSpec with Matchers {

  "AC8033 should" should {

    "validate secretary successfully when no errors present" in {

      val secretary = AC8033(Some("Joana"))

      secretary.validate(null) shouldBe empty
    }

    "validate secretary missing" in {

      val secretary = AC8033(None)

      secretary.validate(null) shouldBe empty
    }

    "validate secretary name length" in {

      val secretary = AC8033(Some("a" * 41))

      val expectedError = Set(CtValidation(Some("AC8033"), "error.AC8033.text.sizeRange", Some(List("1", "40"))))
      secretary.validate(null) shouldBe expectedError
    }

    "validate secretary name characters" in {

      val secretary = AC8033(Some("^^"))

      val expectedError = Set(CtValidation(Some("AC8033"), "error.AC8033.regexFailure", Some(List("^"))))
      secretary.validate(null) shouldBe expectedError
    }


  }
}
