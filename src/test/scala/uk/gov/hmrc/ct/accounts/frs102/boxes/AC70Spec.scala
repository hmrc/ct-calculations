/*
 * Copyright 2016 HM Revenue & Customs
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

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.MockAbridgedAccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC70Spec extends WordSpec with Matchers with MockAbridgedAccountsRetriever {

  "AC70 validation" should {

    "return error if no value entered" in {
      AC70(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC70"), "error.AC70.required"))
    }

    "return no errors if value entered" in {
      AC70(Some(2)).validate(boxRetriever) shouldBe empty
    }

    "be valid when 1" in {
      AC70(Some(1)).validate(boxRetriever) shouldBe empty
    }

    "be valid when greater then 1" in {
      AC70(Some(2)).validate(boxRetriever) shouldBe empty
    }

    "be valid when positive but equals upper limit" in {
      AC70(Some(99999999)).validate(boxRetriever) shouldBe empty
    }

    "fail validation when less then 1" in {
      AC70(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC70"), "error.AC70.below.min", Some(Seq("1", "99999999"))))
    }

    "fail validation when positive but above upper limit" in {
      AC70(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC70"), "error.AC70.above.max", Some(Seq("1", "99999999"))))
    }

  }

}
