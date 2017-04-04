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

package uk.gov.hmrc.ct.accounts

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountStatementValidationFixture[T <: AccountsBoxRetriever] extends WordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  def setupMocks(): Unit = {}

  def doStatementValidationTests(boxId: String, builder: (Option[Boolean]) => ValidatableBox[T]): Unit = {
    setupMocks()
    s"$boxId" should {

      "validate successfully when true" in {
        builder(Some(true)).validate(boxRetriever) shouldBe Set.empty
      }

      "fail validation if not set" in {
        builder(None).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.required", None))
      }

      "fail validation if false" in {
        builder(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.required", None))
      }
    }
  }
}
