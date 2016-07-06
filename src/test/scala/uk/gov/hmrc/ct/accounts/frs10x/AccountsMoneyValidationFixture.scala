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

package uk.gov.hmrc.ct.accounts.frs10x

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

/**
  * Created by jamesdwilliams1 on 06/07/2016.
  */
trait AccountsMoneyValidationFixture extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever: Frs10xAccountsBoxRetriever = mock[Frs10xAccountsBoxRetriever]

  def testAccountsMoneyValidation(boxId: String, builder: (Option[Int]) => ValidatableBox[Frs10xAccountsBoxRetriever]): Unit = {
    s"$boxId" should {
      "be valid when 0" in {
        builder(Some(0)).validate(boxRetriever) shouldBe empty
      }
      "be valid when empty" in {
        builder(None).validate(boxRetriever) shouldBe empty
      }
      "be valid when negative" in {
        builder(Some(-1)).validate(boxRetriever) shouldBe empty
      }
      "be valid when negative and equals lower limit" in {
        builder(Some(-99999999)).validate(boxRetriever) shouldBe empty
      }
      "be valid when positive" in {
        builder(Some(1)).validate(boxRetriever) shouldBe empty
      }
      "be valid when positive but equals upper limit" in {
        builder(Some(99999999)).validate(boxRetriever) shouldBe empty
      }
      "fail validation when negative but below lower limit" in {
        builder(Some(-100000000)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.below.min"))
      }
      "fail validation when positive but above upper limit" in {
        builder(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.above.max"))
      }
    }
  }
}
