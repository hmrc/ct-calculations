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

class E14Spec extends WordSpec with MockitoSugar with Matchers  {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E14 validation" should {
    "throw validation error when E5 > 0 and E14 < 0" in {
      when(boxRetriever.e5()).thenReturn(E5(Some(1)))
      E14(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E14"), "error.E14.mustBeZeroOrPositive"))
    }

    "don't throw validation error when E5 > 0 and E14 = 0" in {
      when(boxRetriever.e5()).thenReturn(E5(Some(1)))
      E14(Some(0)).validate(boxRetriever) shouldBe Set()
    }

    "don't throw validation error when E5 > 0 and E14 > 0" in {
      when(boxRetriever.e5()).thenReturn(E5(Some(1)))
      E14(Some(10)).validate(boxRetriever) shouldBe Set()
    }

    "don't throw validation error when E5 > 0 and E14 = None" in {
      when(boxRetriever.e5()).thenReturn(E5(Some(1)))
      E14(None).validate(boxRetriever) shouldBe Set()
    }

    "throw validation error when E5 = 0 and E14 != None" in {
      when(boxRetriever.e5()).thenReturn(E5(Some(0)))
      E14(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E14"), "error.E14.conditionalMustBeEmpty"))
    }

    "don't throw validation error when E5 = 0 and E14 = None" in {
      when(boxRetriever.e5()).thenReturn(E5(Some(0)))
      E14(None).validate(boxRetriever) shouldBe Set()
    }
  }

}
