/*
 * Copyright 2024 HM Revenue & Customs
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

import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP48Spec extends AnyWordSpec with MockitoSugar with Matchers {

  val boxRetriever = mock[ComputationsBoxRetriever]
  def notEqualError(value: String) = Set(CtValidation(Some("CP48"), "error.CP48.must.equal.CP29", Some(List(value))))

  "CP48" should {
    "not be valid if it is None and CP29 is not" in {
      when(boxRetriever.cp29()).thenReturn(CP29(Some(1)))
      CP48(None).validate(boxRetriever) shouldBe notEqualError("1")
    }
    "not be valid if it is Some and CP29 is not" in {
      when(boxRetriever.cp29()).thenReturn(CP29(None))
      CP48(Some(1)).validate(boxRetriever) shouldBe notEqualError("0")
    }
    "not be valid if it is has a different value to CP29" in {
      when(boxRetriever.cp29()).thenReturn(CP29(Some(1)))
      CP48(Some(2)).validate(boxRetriever) shouldBe notEqualError("1")
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
