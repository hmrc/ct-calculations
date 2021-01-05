/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC200ASpec extends WordSpec
  with Matchers
  with MockitoSugar
  with MockFrs102AccountsRetriever {

  private val boxId = "AC200A"

  private def fieldRequiredError() =
    CtValidation(Some(boxId), s"error.$boxId.required")

  private val validationSuccess: Set[CtValidation] = Set()

  "AC200A" should {
    "fail validation" when {
      "When neither yes or no is selected" in {
        AC200A(None).validate(boxRetriever) shouldBe Set(fieldRequiredError())
      }
    }
    "pass validation" when {
      "yes or no is selected" in {
        AC200A(Some(false)).validate(boxRetriever) shouldBe validationSuccess
        AC200A(Some(true)).validate(boxRetriever) shouldBe validationSuccess
      }
    }
  }
}
