/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

class E3Spec extends WordSpec with MockitoSugar with Matchers  {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E3 validation" should {
    "make E3 required when E2 > E1" in {
      when(boxRetriever.e1()).thenReturn(E1(Some(1)))
      when(boxRetriever.e2()).thenReturn(E2(Some(2)))
      E3(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("E3"), "error.E3.conditionalRequired", None))
    }
    "make E3 required when E2 set but E1 is empty" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(Some(2)))
      E3(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("E3"), "error.E3.conditionalRequired", None))
    }

    "enforce E3 empty when E2 < E1" in {
      when(boxRetriever.e1()).thenReturn(E1(Some(2)))
      when(boxRetriever.e2()).thenReturn(E2(Some(1)))
      E3(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E3"), "error.E3.conditionalMustBeEmpty", None))
    }
    "make E3 not required when E2 empty but E1 set" in {
      when(boxRetriever.e1()).thenReturn(E1(Some(1)))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E3(None).validate(boxRetriever) shouldBe Set()
    }

    "make E3 not required when E2 and E1 are both empty" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E3(None).validate(boxRetriever) shouldBe Set()
    }

    "E3 invalid if number less that 1" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E3(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E3"), "error.E3.outOfRange", None))
    }
    "E3 valid if number 1 or greater" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E3(Some(1)).validate(boxRetriever) shouldBe Set()
    }
  }


}
