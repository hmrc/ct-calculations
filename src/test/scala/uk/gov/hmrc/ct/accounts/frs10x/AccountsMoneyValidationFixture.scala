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

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.AC205
import uk.gov.hmrc.ct.accounts.frs10x.abridged._
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountsMoneyValidationFixture extends WordSpec with Matchers with MockitoSugar {

  self: MockAbridgedAccountsRetriever =>

  def setUpMocks(): Unit = {
    when(boxRetriever.ac16()).thenReturn(AC16(Some(16)))
    when(boxRetriever.ac18()).thenReturn(AC18(Some(18)))
    when(boxRetriever.ac20()).thenReturn(AC20(Some(20)))
    when(boxRetriever.ac28()).thenReturn(AC28(Some(28)))
    when(boxRetriever.ac30()).thenReturn(AC30(Some(30)))
    when(boxRetriever.ac34()).thenReturn(AC34(Some(34)))
    when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
  }

  def testAccountsMoneyValidation(boxId: String, builder: (Option[Int]) => ValidatableBox[AbridgedAccountsBoxRetriever]): Unit = {
    setUpMocks()
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
        builder(Some(-100000000)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.below.min", Some(Seq("-99999999", "99999999"))))
      }
      "fail validation when positive but above upper limit" in {
        builder(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.above.max", Some(Seq("-99999999", "99999999"))))
      }
    }
  }

  def testAccountsMoneyValidationWithMin(boxId: String, minValue: Int, builder: (Option[Int]) => ValidatableBox[AbridgedAccountsBoxRetriever]): Unit = {
    setUpMocks()
    s"$boxId" should {
      "be valid when minimum" in {
        builder(Some(minValue)).validate(boxRetriever) shouldBe empty
      }
      "be valid when empty" in {
        builder(None).validate(boxRetriever) shouldBe empty
      }
      "be valid when greater then min" in {
        builder(Some(minValue + 1)).validate(boxRetriever) shouldBe empty
      }
      "be valid when positive but equals upper limit" in {
        builder(Some(99999999)).validate(boxRetriever) shouldBe empty
      }
      "fail validation when less then min lower limit" in {
        builder(Some(minValue - 1)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.below.min", Some(Seq(minValue.toString, "99999999"))))
      }
      "fail validation when positive but above upper limit" in {
        builder(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.above.max", Some(Seq(minValue.toString, "99999999"))))
      }
    }
  }
}
