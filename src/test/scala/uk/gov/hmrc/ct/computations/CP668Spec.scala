/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import org.mockito.Mockito._
import uk.gov.hmrc.ct.{BoxValidationFixture, CATO22}
import uk.gov.hmrc.ct.box.CtValidation

class CP668Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  val boxRetriever = mock[ComputationsBoxRetriever]

  "CP668" should {

    "be mandatory when CPQ8 is true and CPAux3 + CP666 > CP667" in {

      val mockBoxRetriever = setupMockRetriever

      when(mockBoxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(mockBoxRetriever.cpAux3()).thenReturn(CPAux3(50))
      when(mockBoxRetriever.cp666()).thenReturn(CP666(50))
      when(mockBoxRetriever.cp667()).thenReturn(CP667(50))

      CP668(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("CP668"), "error.CP668.specialRatePoolAllowanceRequired"))
    }

    "not return an error when CPQ8 is true and CPAux3 + CP666 > CP667 and CP668 has a value" in {

      val mockBoxRetriever = setupMockRetriever

      when(mockBoxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(mockBoxRetriever.cpAux3()).thenReturn(CPAux3(50))
      when(mockBoxRetriever.cp666()).thenReturn(CP666(50))
      when(mockBoxRetriever.cp667()).thenReturn(CP667(50))

      CP668(0).validate(mockBoxRetriever) shouldBe empty
    }

    "return an error when CPQ8 is true and CPAux3 + CP666 > CP667 and CP668 has a negative value" in {

      val mockBoxRetriever = setupMockRetriever

      when(mockBoxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(mockBoxRetriever.cpAux3()).thenReturn(CPAux3(50))
      when(mockBoxRetriever.cp666()).thenReturn(CP666(50))
      when(mockBoxRetriever.cp667()).thenReturn(CP667(50))

      CP668(-20).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("CP668"), "error.CP668.mustBeZeroOrPositive"))
    }

    "not be mandatory when CPQ8 is true" in {

      val mockBoxRetriever = setupMockRetriever

      when(mockBoxRetriever.cpQ8()).thenReturn(CPQ8(Some(true)))
      when(mockBoxRetriever.cpAux3()).thenReturn(CPAux3(50))
      when(mockBoxRetriever.cp666()).thenReturn(CP666(50))
      when(mockBoxRetriever.cp667()).thenReturn(CP667(50))

      CP668(None).validate(mockBoxRetriever) shouldBe empty
    }

    "not be mandatory when CPQ8 is None" in {

      val mockBoxRetriever = setupMockRetriever

      when(mockBoxRetriever.cpQ8()).thenReturn(CPQ8(None))
      when(mockBoxRetriever.cpAux3()).thenReturn(CPAux3(50))
      when(mockBoxRetriever.cp666()).thenReturn(CP666(50))
      when(mockBoxRetriever.cp667()).thenReturn(CP667(50))

      CP668(None).validate(mockBoxRetriever) shouldBe empty
    }

    "not be mandatory when CPQ8 is false and CPAux3 + CP666 IS < CP667" in {

      val mockBoxRetriever = setupMockRetriever

      when(mockBoxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(mockBoxRetriever.cpAux3()).thenReturn(CPAux3(50))
      when(mockBoxRetriever.cp666()).thenReturn(CP666(0))
      when(mockBoxRetriever.cp667()).thenReturn(CP667(50))

      CP668(None).validate(mockBoxRetriever) shouldBe empty
    }

    "not be mandatory when CPQ8 is false and CP667 is large" in {

      val mockBoxRetriever = setupMockRetriever

      when(mockBoxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(mockBoxRetriever.cpAux3()).thenReturn(CPAux3(50))
      when(mockBoxRetriever.cp666()).thenReturn(CP666(50))
      when(mockBoxRetriever.cp667()).thenReturn(CP667(100))

      CP668(None).validate(mockBoxRetriever) shouldBe empty
    }

    testCannotExistWhen("CP668", CP668.apply) {
      val boxRetriever = setupMockRetriever
      when(boxRetriever.cato22()).thenReturn(CATO22(10))
      when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(boxRetriever.cpAux3()).thenReturn(CPAux3(50))
      when(boxRetriever.cp666()).thenReturn(CP666(1000))
      when(boxRetriever.cp667()).thenReturn(CP667(0))

      when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(true))).getMock[ComputationsBoxRetriever]
    }

  }

  private def setupMockRetriever: ComputationsBoxRetriever = {
    val mockRetriever = mock[ComputationsBoxRetriever]

    when(mockRetriever.cato22()).thenReturn(CATO22(0))

    mockRetriever
  }

}
