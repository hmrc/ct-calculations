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

class CP302Spec extends WordSpec with Matchers with MockitoSugar {

  "CP302" when {
    val boxRetriever = mock[ComputationsBoxRetriever]
    "is negative" should {
      "not validate" in {
        CP302(-1).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP302"), "error.CP302.mustBeZeroOrPositive"))
      }
    }
  }
}
