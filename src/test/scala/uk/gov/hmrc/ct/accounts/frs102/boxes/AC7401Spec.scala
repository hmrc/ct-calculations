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

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7401Spec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[AbridgedAccountsBoxRetriever]

  "AC7401" should {

    "pass validation when not populated if AC4700 not set" in {
      when (boxRetriever.ac7400()).thenReturn (AC7400(None))

      AC7401(None).validate(boxRetriever) shouldBe empty
    }

    "pass validation when not populated if AC4700 false" in {
      when (boxRetriever.ac7400()).thenReturn (AC7400(Some(false)))

      AC7401(None).validate(boxRetriever) shouldBe empty
    }

    "give cannot be set error when populated if AC4700 not true" in {
      when (boxRetriever.ac7400()).thenReturn (AC7400(Some(false)))

      AC7401(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7401"), "error.AC7401.cannot.exist", None))
      AC7401(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7401"), "error.AC7401.cannot.exist", None))
      AC7401(Some("legal content")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7401"), "error.AC7401.cannot.exist", None))
    }

    "pass validation when legal and AC4700 true" in {
      when (boxRetriever.ac7400()).thenReturn (AC7400(Some(true)))

      AC7401(Some("l")).validate(boxRetriever) shouldBe empty
      AC7401(Some("legal content")).validate(boxRetriever) shouldBe empty
    }

    "fail appropriate validations when AC4700 true" in {
      when (boxRetriever.ac7400()).thenReturn (AC7400(Some(true)))

      AC7401(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7401"), "error.AC7401.required", None))
      AC7401(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7401"), "error.AC7401.required", None))
      AC7401(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7401"),"error.AC7401.regexFailure",Some(List("^"))))
    }
  }
}
