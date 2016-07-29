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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.validation

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait ValidateAssetsEqualSharesSpec extends WordSpec with Matchers with MockitoSugar {

  def addOtherBoxValue100Mock(mockRetriever: Frs10xAccountsBoxRetriever): Unit

  def addOtherBoxValueNoneMock(mockRetriever: Frs10xAccountsBoxRetriever): Unit

  def testAssetsEqualToSharesValidation(boxId: String, builder: (Option[Int]) => ValidatableBox[Frs10xAccountsBoxRetriever]): Unit = {

    s"$boxId" should {

      "return an error if it has a different value to other box" in {
        val retriever = mock[Frs10xAccountsBoxRetriever]

        addOtherBoxValue100Mock(retriever)

        builder(Some(50)).validate(retriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.assetsNotEqualToShares"))
      }

      "return an error if it has a value and other box is None" in {
        val retriever = mock[Frs10xAccountsBoxRetriever]

        addOtherBoxValueNoneMock(retriever)

        builder(Some(100)).validate(retriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.assetsNotEqualToShares"))
      }

      "return no error if both value are None" in {
        val retriever = mock[Frs10xAccountsBoxRetriever]

       addOtherBoxValueNoneMock(retriever)

        builder(None).validate(retriever) shouldBe empty
      }

      "return no error if they have the same values" in {
        val retriever = mock[Frs10xAccountsBoxRetriever]

        addOtherBoxValue100Mock(retriever)

        builder(Some(100)).validate(retriever) shouldBe empty
      }
    }
  }
}
