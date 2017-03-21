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
import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.{BoxValidationFixture, CATO21}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP89Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  val boxRetriever = mock[ComputationsBoxRetriever]

  "CP89" should {

    "be mandatory if CPQ8 is false and CPAux2 + CP78 > CP672" in {

      val mockRetriever = setupRetriever()

      when(mockRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(mockRetriever.cpAux2()).thenReturn(CPAux2(50))
      when(mockRetriever.cp78()).thenReturn(CP78(Some(50)))
      when(mockRetriever.cp672()).thenReturn(CP672(Some(50)))

      CP89(None).validate(mockRetriever) shouldBe Set(CtValidation(Some("CP89"), "error.CP89.mainPoolAllowanceRequired"))
    }

    "return no error if CPQ8 is false and CPAux2 + CP78 > CP672 and CP89 has a value of 0" in {

      val mockRetriever = setupRetriever()

      when(mockRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(mockRetriever.cpAux2()).thenReturn(CPAux2(50))
      when(mockRetriever.cp78()).thenReturn(CP78(Some(50)))
      when(mockRetriever.cp672()).thenReturn(CP672(Some(50)))

      CP89(Some(0)).validate(mockRetriever) shouldBe Set.empty
    }

    "return a negative number error if CPQ8 is false and CPAux2 + CP78 > CP672 and CP89 has a negative value" in {

      val mockRetriever = setupRetriever()

      when(mockRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(mockRetriever.cpAux2()).thenReturn(CPAux2(50))
      when(mockRetriever.cp78()).thenReturn(CP78(Some(50)))
      when(mockRetriever.cp672()).thenReturn(CP672(Some(50)))

      CP89(Some(-20)).validate(mockRetriever) shouldBe Set(CtValidation(Some("CP89"), "error.CP89.mustBeZeroOrPositive"))
    }

    "not be mandatory if CPQ8 is true" in {

      val mockRetriever = setupRetriever()

      when(mockRetriever.cpQ8()).thenReturn(CPQ8(Some(true)))
      when(mockRetriever.cpAux2()).thenReturn(CPAux2(50))
      when(mockRetriever.cp78()).thenReturn(CP78(Some(50)))
      when(mockRetriever.cp672()).thenReturn(CP672(Some(50)))

      CP89(None).validate(mockRetriever) shouldBe empty
    }

    "not be mandatory if CPQ8 is false and CPAux2 + CP78 is equal to then CP672" in {

      val mockRetriever = setupRetriever()

      when(mockRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(mockRetriever.cpAux2()).thenReturn(CPAux2(25))
      when(mockRetriever.cp78()).thenReturn(CP78(Some(25)))
      when(mockRetriever.cp672()).thenReturn(CP672(Some(50)))

      CP89(None).validate(mockRetriever) shouldBe empty
    }

    "not be mandatory if CPQ8 is false and CP672 is greater then CPAux2 + CP78" in {

      val mockRetriever = setupRetriever()

      when(mockRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
      when(mockRetriever.cpAux2()).thenReturn(CPAux2(25))
      when(mockRetriever.cp78()).thenReturn(CP78(Some(25)))
      when(mockRetriever.cp672()).thenReturn(CP672(Some(100)))

      CP89(None).validate(mockRetriever) shouldBe empty
    }

    testBecauseOfDependendBoxThenCannotExist("CP89", CP89.apply) {
      val boxRetriever = setupRetriever
      when(boxRetriever.cato21()).thenReturn(CATO21(10))
      when(boxRetriever.cp81()).thenReturn(CP81(1000))
      when(boxRetriever.cp88()).thenReturn(CP88(0))
      when(boxRetriever.cpAux2()).thenReturn(CPAux2(150))
      when(boxRetriever.cp78()).thenReturn(CP78(Some(50)))
      when(boxRetriever.cp672()).thenReturn(CP672(Some(50)))
      when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(true))).getMock[ComputationsBoxRetriever]
    }

  }

  private def setupRetriever(): ComputationsBoxRetriever = {
    val mockRetriever = mock[ComputationsBoxRetriever]

    when(mockRetriever.cp81()).thenReturn(CP81(0))
    when(mockRetriever.cp82()).thenReturn(CP82(0))
    when(mockRetriever.cp83()).thenReturn(CP83(0))
    when(mockRetriever.cp87()).thenReturn(CP87(0))
    when(mockRetriever.cp88()).thenReturn(CP88(0))
    when(mockRetriever.cpAux1()).thenReturn(CPAux1(0))
    when(mockRetriever.cato21()).thenReturn(CATO21(0))

    mockRetriever
  }

}
