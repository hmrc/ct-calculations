/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.computations

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import org.mockito.Mockito._
import uk.gov.hmrc.ct.CATO22
import uk.gov.hmrc.ct.box.CtValidation

class CP668Spec extends WordSpec with Matchers with MockitoSugar {

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

    "not care about 0 value when CPQ8 is true" in {

      val mockBoxRetriever = setupMockRetriever

      when(mockBoxRetriever.cpQ8()).thenReturn(CPQ8(Some(true)))
      when(mockBoxRetriever.cpAux3()).thenReturn(CPAux3(50))
      when(mockBoxRetriever.cp666()).thenReturn(CP666(50))
      when(mockBoxRetriever.cp667()).thenReturn(CP667(50))

      CP668(-20).validate(mockBoxRetriever) shouldBe empty
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

  }

  private def setupMockRetriever: ComputationsBoxRetriever = {
    val mockRetriever = mock[ComputationsBoxRetriever]

    when(mockRetriever.cato22()).thenReturn(CATO22(0))

    mockRetriever
  }

}
