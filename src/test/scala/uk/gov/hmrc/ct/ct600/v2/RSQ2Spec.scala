/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import org.mockito.Mockito._
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.computations.CP287
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class RSQ2Spec extends WordSpec with Matchers with MockitoSugar {

  "RSQ2 validation" should {

    "return no error if input value populated and CP287 has no value" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(None))

      RSQ2(Some(true)).validate(boxRetriever) shouldBe empty
    }

    "return a mandatory error if no input value populated and CP287 has no value" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(None))

      RSQ2(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("RSQ2"), "error.RSQ2.required"))
    }

    "return a mandatory error if no input value populated and CP287 has value 0" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(Some(0)))

      RSQ2(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("RSQ2"), "error.RSQ2.required"))
    }

    "return a mandatory error if no input value populated, and not a computations box retriever" in {
      val boxRetriever = mock[BoxRetriever]

      RSQ2(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("RSQ2"), "error.RSQ2.required"))
    }

    "return no error if input value populated, and not a computations box retriever" in {
      val boxRetriever = mock[BoxRetriever]

      RSQ2(Some(false)).validate(boxRetriever) shouldBe empty
    }

    "return a box should not exist error if there is an input value populated and CP287 has a value" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(Some(10)))

      RSQ2(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("RSQ2"), "error.RSQ2.cannot.exist"))
    }

    "return no error if there is no input value populated and CP287 has a value" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(Some(10)))

      RSQ2(None).validate(boxRetriever) shouldBe empty
    }

    "return no error if there is an input value populated and CP287 has a value of 0" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(Some(0)))

      RSQ2(Some(false)).validate(boxRetriever) shouldBe empty
    }

  }

}
