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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC8085Spec extends WordSpec
                 with MockitoSugar
                 with Matchers
                 with BeforeAndAfter
                 with MockFrs102AccountsRetriever {

  "AC8085" should {

    "fail validation if is empty" in {
      AC8085(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC8085"), "error.AC8085.required"))
    }

    "pass validation if is not empty" in {
      AC8085(Some(true)).validate(boxRetriever) shouldBe Set.empty
    }

  }

}
