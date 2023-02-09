/*
 * Copyright 2023 HM Revenue & Customs
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
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
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

