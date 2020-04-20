/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7901Spec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[AbridgedAccountsBoxRetriever]

  "AC7901" should {

    "pass validation when not populated if AC4700 not set" in {
      when (boxRetriever.ac7900()).thenReturn (AC7900(None))

      AC7901(None).validate(boxRetriever) shouldBe empty
    }

    "pass validation when not populated if AC4700 false" in {
      when (boxRetriever.ac7900()).thenReturn (AC7900(Some(false)))

      AC7901(None).validate(boxRetriever) shouldBe empty
    }

    "give cannot be set error when populated if AC4700 not true" in {
      when (boxRetriever.ac7900()).thenReturn (AC7900(Some(false)))

      AC7901(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.cannot.exist", None))
      AC7901(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.cannot.exist", None))
      AC7901(Some("legal content")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.cannot.exist", None))
    }

    "pass validation when legal and AC4700 true" in {
      when (boxRetriever.ac7900()).thenReturn (AC7900(Some(true)))

      AC7901(Some("l")).validate(boxRetriever) shouldBe empty
      AC7901(Some("legal content")).validate(boxRetriever) shouldBe empty
    }

    "fail appropriate validations when AC4700 true" in {
      when (boxRetriever.ac7900()).thenReturn (AC7900(Some(true)))

      AC7901(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.required", None))
      AC7901(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.required", None))
      AC7901(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.regexFailure", Some(List("^"))))
    }
  }
}
