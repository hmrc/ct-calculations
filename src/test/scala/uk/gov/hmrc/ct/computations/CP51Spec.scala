/*
 * Copyright 2021 HM Revenue & Customs
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
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP51Spec  extends WordSpec with MockitoSugar with Matchers {

  val boxRetriever = mock[ComputationsBoxRetriever]

//  "CP51" should {

//    " be valid if  cp 33 is NEGATIVE and someone enters in the same number " in {
//      when(boxRetriever.cp33()).thenReturn(CP33(Some(-10)))
//      CP51(Some(-10)).validate(boxRetriever) shouldBe Set()
//    }

//    " be valid if  cp 33 is NEGATIVE and someone enters in Zero " in {
//      when(boxRetriever.cp33()).thenReturn(CP33(Some(-10)))
//      CP51(Some(0)).validate(boxRetriever) shouldBe Set()
//    }

//    " be valid if  cp 33 is NEGATIVE and someone enters in a greater negative number " in {
//      when(boxRetriever.cp33()).thenReturn(CP33(Some(-10)))
//      CP51(Some(-2)).validate(boxRetriever) shouldBe Set()
//    }

//    " be in valid if someone enters in positive value  " in {
//      CP51(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP51"),"error.CP51.mustBeNegativeOrZero",None))
//    }

//    " be in valid if  cp 33 is positive and someone enters in value  " in {
//      when(boxRetriever.cp33()).thenReturn(CP33(Some(10)))
//      CP51(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP51"), "error.CP57.CP51.must.be.mutually.exclusive"))
//    }

//    " be in valid if  cp 33 is negative and someone enters in value that is more negative " in {
//      when(boxRetriever.cp33()).thenReturn(CP33(Some(-10)))
//      CP51(Some(-11)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP51"), "error.CP51.must.be.less.then.CP33"))
//    }
//  }
}
