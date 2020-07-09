/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7601Spec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[AbridgedAccountsBoxRetriever]

  "AC7601" should {

    "pass validation when not populated if AC4700 not set" in {
      when (boxRetriever.ac7600()).thenReturn (AC7600(None))

      AC7601(None).validate(boxRetriever) shouldBe empty
    }

    "pass validation when not populated if AC4700 false" in {
      when (boxRetriever.ac7600()).thenReturn (AC7600(Some(false)))

      AC7601(None).validate(boxRetriever) shouldBe empty
    }

    "give cannot be set error when populated if AC4700 not true" in {
      when (boxRetriever.ac7600()).thenReturn (AC7600(Some(false)))

      AC7601(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7601"), "error.AC7601.cannot.exist", None))
      AC7601(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7601"), "error.AC7601.cannot.exist", None))
      AC7601(Some("legal content")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7601"), "error.AC7601.cannot.exist", None))
    }

    "pass validation when legal and AC4700 true" in {
      when (boxRetriever.ac7600()).thenReturn (AC7600(Some(true)))

      AC7601(Some("l")).validate(boxRetriever) shouldBe empty
      AC7601(Some("legal content")).validate(boxRetriever) shouldBe empty
    }

    "fail appropriate validations when AC4700 true" in {
      when (boxRetriever.ac7600()).thenReturn (AC7600(Some(true)))

      AC7601(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7601"), "error.AC7601.required", None))
      AC7601(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7601"), "error.AC7601.required", None))
      AC7601(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7601"), "error.AC7601.regexFailure", Some(List("^"))))
    }
  }
}
