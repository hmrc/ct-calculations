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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7100Spec extends AnyWordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[Frs10xDirectorsBoxRetriever]

  "AC7100" should {
    "pass validation if true" in {
      AC7100(Some(true)).validate(boxRetriever) shouldBe empty
    }
    "fail validation if false" in {
      AC7100(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7100"), "error.AC7100.required.true"))
    }
    "fail validation if not answered" in {
      AC7100(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7100"), "error.AC7100.required.true"))
    }
  }

}
