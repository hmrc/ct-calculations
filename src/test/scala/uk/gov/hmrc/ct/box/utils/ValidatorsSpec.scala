/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box.utils

import uk.gov.hmrc.ct.box.{CtValidation, Validators}
import uk.gov.hmrc.ct.utils.UnitSpec

class ValidatorsSpec extends UnitSpec with Validators {

  "errorMessage" should {

    val required = "required"
    val errorArg1 = 1
    val errorArg2 = 2
    val errorMessageKey = s"error.$boxId.$required"

    "return an error message without an argument" in {
      errorMessage(required) shouldBe fieldRequiredError(boxId)
    }

    "return an error message with one argument" in {
      errorMessage(required, Seq(errorArg1)) shouldBe
        Set(CtValidation(Some(boxId), errorMessageKey, Some(Seq(errorArg1.toString))))
    }

    "return an error message with multiple arguments" in {
      errorMessage(required, Seq(errorArg1, errorArg2)) shouldBe
        Set(CtValidation(Some(boxId), errorMessageKey, Some(Seq(errorArg1.toString, errorArg2.toString))))
    }
  }
}
