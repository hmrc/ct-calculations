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

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.accounts.frs102.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7901Spec extends AnyWordSpec with Matchers with MockitoSugar {

  val boxRetriever: AbridgedAccountsBoxRetriever = mock[AbridgedAccountsBoxRetriever]

  "AC7901" should {

    "pass validation when not populated if AC4700 not set" in {
      when (boxRetriever.ac7900()).thenReturn (AC7900(None))

      AC7901(None).validate(boxRetriever) shouldBe empty
    }

    "pass validation when not populated if AC4700 false" in {
      when (boxRetriever.ac7900()).thenReturn (AC7900(Some(false)))

      AC7901(None).validate(boxRetriever) shouldBe empty
    }

    "give cannot be set error when populated if AC4700 not true" in {
      when (boxRetriever.ac7900()).thenReturn (AC7900(Some(false)))

      AC7901(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.cannot.exist", None))
      AC7901(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.cannot.exist", None))
      AC7901(Some("legal content")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.cannot.exist", None))
    }

    "pass validation when legal and AC4700 true" in {
      when (boxRetriever.ac7900()).thenReturn (AC7900(Some(true)))

      AC7901(Some("l")).validate(boxRetriever) shouldBe empty
      AC7901(Some("legal content")).validate(boxRetriever) shouldBe empty
    }

    "fail appropriate validations when AC4700 true" in {
      when (boxRetriever.ac7900()).thenReturn (AC7900(Some(true)))

      AC7901(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.required", None))
      AC7901(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.required", None))
      AC7901(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7901"), "error.AC7901.regexFailure", Some(List("^"))))
    }
  }
}
