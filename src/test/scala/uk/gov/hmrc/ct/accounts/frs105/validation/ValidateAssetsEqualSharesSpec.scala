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

package uk.gov.hmrc.ct.accounts.frs105.validation

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait ValidateAssetsEqualSharesSpec extends WordSpec with Matchers with MockitoSugar {

  def addOtherBoxValue100Mock(mockRetriever: Frs105AccountsBoxRetriever): Unit

  def addOtherBoxValueNoneMock(mockRetriever: Frs105AccountsBoxRetriever): Unit

  def testAssetsEqualToSharesValidation(boxId: String, builder: (Option[Int]) => ValidatableBox[Frs105AccountsBoxRetriever]): Unit = {

    s"$boxId" should {

      "return an error if it has a different value to other box" in {
        val retriever = mock[Frs105AccountsBoxRetriever]

        addOtherBoxValue100Mock(retriever)

        builder(Some(50)).validate(retriever) shouldBe Set(CtValidation(None, s"error.$boxId.assetsNotEqualToShares"))
      }

      "return an error if it has a value and other box is None" in {
        val retriever = mock[Frs105AccountsBoxRetriever]

        addOtherBoxValueNoneMock(retriever)

        builder(Some(100)).validate(retriever) shouldBe Set(CtValidation(None, s"error.$boxId.assetsNotEqualToShares"))
      }

      "return no error if both value are None" in {
        val retriever = mock[Frs105AccountsBoxRetriever]

       addOtherBoxValueNoneMock(retriever)

        builder(None).validate(retriever) shouldBe empty
      }

      "return no error if they have the same values" in {
        val retriever = mock[Frs105AccountsBoxRetriever]

        addOtherBoxValue100Mock(retriever)

        builder(Some(100)).validate(retriever) shouldBe empty
      }
    }
  }
}
