/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP48Spec extends WordSpec with MockitoSugar with Matchers {

  val boxRetriever = mock[ComputationsBoxRetriever]

  "CP48" should {
    "not be valid if it is None and CP29 is not" in {
      when(boxRetriever.cp29()).thenReturn(CP29(Some(1)))
      CP48(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP48"), "error.CP48.must.equal.CP29", Some(List("1"))))
    }
    "not be valid if it is Some and CP29 is not" in {
      when(boxRetriever.cp29()).thenReturn(CP29(None))
      CP48(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP48"), "error.CP48.must.equal.CP29", Some(List("0"))))
    }
    "not be valid if it is has a different value to CP29" in {
      when(boxRetriever.cp29()).thenReturn(CP29(Some(1)))
      CP48(Some(2)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP48"), "error.CP48.must.equal.CP29", Some(List("1"))))
    }
    "not be valid if it is has the same negative value as CP29" in {
      when(boxRetriever.cp29()).thenReturn(CP29(Some(-1)))
      CP48(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP48"), "error.CP48.mustBeZeroOrPositive"))
    }
    "be valid if it is has the same value as CP29" in {
      when(boxRetriever.cp29()).thenReturn(CP29(Some(1)))
      CP48(Some(1)).validate(boxRetriever) shouldBe empty
    }
    "be valid if it is both it and CP29 are 0" in {
      when(boxRetriever.cp29()).thenReturn(CP29(Some(0)))
      CP48(Some(0)).validate(boxRetriever) shouldBe empty
    }
    "be valid if it is both it and CP29 are None" in {
      when(boxRetriever.cp29()).thenReturn(CP29(None))
      CP48(None).validate(boxRetriever) shouldBe empty
    }
  }
}
