/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v2

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

class E15Spec extends WordSpec with MockitoSugar with Matchers  {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E15 validation" should {
    "throw validation error when E7 > 0 and E15 < 0" in {
      when(boxRetriever.e7()).thenReturn(E7(Some(1)))
      E15(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E15"), "error.E15.mustBeZeroOrPositive"))
    }

    "don't throw validation error when E7 > 0 and E15 = 0" in {
      when(boxRetriever.e7()).thenReturn(E7(Some(1)))
      E15(Some(0)).validate(boxRetriever) shouldBe Set()
    }

    "don't throw validation error when E7 > 0 and E15 > 0" in {
      when(boxRetriever.e7()).thenReturn(E7(Some(1)))
      E15(Some(10)).validate(boxRetriever) shouldBe Set()
    }

    "don't throw validation error when E7 > 0 and E15 = None" in {
      when(boxRetriever.e7()).thenReturn(E7(Some(1)))
      E15(None).validate(boxRetriever) shouldBe Set()
    }

    "throw validation error when E7 = 0 and E15 != None" in {
      when(boxRetriever.e7()).thenReturn(E7(Some(0)))
      E15(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E15"), "error.E15.conditionalMustBeEmpty"))
    }

    "don't throw validation error when E7 = 0 and E15 = None" in {
      when(boxRetriever.e7()).thenReturn(E7(Some(0)))
      E15(None).validate(boxRetriever) shouldBe Set()
    }
  }

}
