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

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountsRequiredValidationFixture[T <: AccountsBoxRetriever] extends WordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  def testAccountsRequiredValidation(boxId: String, builder: (Option[Int]) => ValidatableBox[T], PYBoxRetriever: ValidatableBox[T], PYBox : (Option[Int]) => ValidatableBox[T], boxRetriever: T): Unit = {

    s"${boxId}" when {
      "it is undefined" when {
        "Previous Year value is defined" should {
          "error that it is mandatory" in {
            when(PYBoxRetriever).thenReturn(PYBox(Some(1)))
            builder(None).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.${boxId}.required"))
          }
        }

        "it is defined" when {
          "Previous Year value is undefined" should {
            "not error" in {
              when(PYBoxRetriever).thenReturn(PYBox(None))
              builder(None).validate(boxRetriever) shouldBe Set.empty
            }
          }
        }
      }

      "it is defined" when {
        "Previous Year value is defined" should {
          "not error" in {
            when(PYBoxRetriever).thenReturn(PYBox(None))
            builder(Some(1)).validate(boxRetriever) shouldBe Set.empty
          }
        }

        "Previous Year value is undefined" should {
          "not error" in {
            when(PYBoxRetriever).thenReturn(PYBox(None))
            builder(Some(1)).validate(boxRetriever) shouldBe Set.empty
          }
        }
      }
    }
  }

}
