/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.version.calculations.ComputationsBoxRetrieverForTest
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar

class CP503Spec extends WordSpec with MockitoSugar with Matchers {

  "validateNotExceedingCP501" should {
    "return validation error if value is more that CP501" in {
      val mockBoxRetriever = mock[ComputationsBoxRetrieverForTest]
      when(mockBoxRetriever.cp501()).thenReturn(CP501(Some(0)))
      CP503(Some(2)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("CP503"), "error.CP503.propertyExpensesExceedsIncome", None))
    }
    "return validation error if value is set but CP501 is not set" in {
      val mockBoxRetriever = mock[ComputationsBoxRetrieverForTest]
      when(mockBoxRetriever.cp501()).thenReturn(CP501(None))
      CP503(Some(2)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("CP503"), "error.CP503.propertyExpensesExceedsIncome", None))
    }


    "return no validation error if value is less that CP501" in {
      val mockBoxRetriever = mock[ComputationsBoxRetrieverForTest]
      when(mockBoxRetriever.cp501()).thenReturn(CP501(Some(3)))
      CP503(Some(2)).validate(mockBoxRetriever) shouldBe Set()
    }
    "return no validation error if value is equal CP501" in {
      val mockBoxRetriever = mock[ComputationsBoxRetrieverForTest]
      when(mockBoxRetriever.cp501()).thenReturn(CP501(Some(1)))
      CP503(Some(1)).validate(mockBoxRetriever) shouldBe Set()
    }
    "return no validation error if value is not set but CP501 is" in {
      val mockBoxRetriever = mock[ComputationsBoxRetrieverForTest]
      when(mockBoxRetriever.cp501()).thenReturn(CP501(Some(1)))
      CP503(None).validate(mockBoxRetriever) shouldBe Set()
    }
    "return no validation error if value is not set and CP501 is also not set" in {
      val mockBoxRetriever = mock[ComputationsBoxRetrieverForTest]
      when(mockBoxRetriever.cp501()).thenReturn(CP501(None))
      CP503(None).validate(mockBoxRetriever) shouldBe Set()
    }
    "return no validation error if value is 0 and CP501 is not set" in {
      val mockBoxRetriever = mock[ComputationsBoxRetrieverForTest]
      when(mockBoxRetriever.cp501()).thenReturn(CP501(None))
      CP503(Some(0)).validate(mockBoxRetriever) shouldBe Set()
    }

  }

}
