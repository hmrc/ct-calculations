/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP57Spec extends WordSpec with MockitoSugar with Matchers {

  val boxRetriever = mock[ComputationsBoxRetriever]
/*
 "CP57" should {

    "not be valid if  cp 33 is positive and cp51 is a higher number then it" in {
      when(boxRetriever.cp33()).thenReturn(CP33(Some(10)))
      CP57(Some(11)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP57"), "error.CP57.must.be.less.then.CP33"))
    }
    "be valid if  cp 33 is positive and cp51 is a lower number then it" in {
      when(boxRetriever.cp33()).thenReturn(CP33(Some(10)))
      CP57(Some(9)).validate(boxRetriever) shouldBe Set()
    }
    "not be valid if  cp 33 is negative and someone enters a value " in {
      when(boxRetriever.cp33()).thenReturn(CP33(Some(-10)))
      CP57(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP57"), "error.CP57.CP51.must.be.mutually.exclusive"))
    }

    " be valid if  cp 33 is negative and no value is entered " in {
      when(boxRetriever.cp33()).thenReturn(CP33(Some(-10)))
      CP57(None).validate(boxRetriever) shouldBe Set()
    }

    " be valid if  cp 33 is negative and Zero is entered " in {
      when(boxRetriever.cp33()).thenReturn(CP33(Some(-10)))
      CP57(Some(0)).validate(boxRetriever) shouldBe Set()
    }

    " be valid if  cp 33 is positive and someone enters in the same number " in {
      when(boxRetriever.cp33()).thenReturn(CP33(Some(10)))
      CP57(Some(10)).validate(boxRetriever) shouldBe Set()
    }
  }*/

}

