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
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP8Spec extends WordSpec with Matchers with MockitoSugar {


  val boxRetriever = mock[ComputationsBoxRetriever]

  "CP8 validation" should {
    "pass when zero" in {
      CP8(Some(0)).validate(boxRetriever) shouldBe empty
    }
    "pass when at max" in {
      CP8(Some(99999999)).validate(boxRetriever) shouldBe empty
    }
    "pass when at min" in {
      CP8(Some(-99999999)).validate(boxRetriever) shouldBe empty
    }
    "fail when below min" in {
      CP8(Some(-100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP8"), "error.CP8.below.min", Some(Seq("-99999999", "99999999"))))
    }
    "fail when above max" in {
      CP8(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP8"), "error.CP8.above.max", Some(Seq("-99999999", "99999999"))))
    }
    "fail when empty" in {
      CP8(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP8"), "error.CP8.required"))
    }
  }
  
}

case class CP8Holder(cp8: CP8)
