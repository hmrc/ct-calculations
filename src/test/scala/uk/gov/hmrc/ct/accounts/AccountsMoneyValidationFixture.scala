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

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountsMoneyValidationFixture[T <: AccountsBoxRetriever] extends WordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  val STANDARD_MIN = -99999999
  val STANDARD_MAX = 99999999

  def setUpMocks(): Unit = {
    when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
  }

  def testAccountsMoneyValidation(boxId: String, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean = true): Unit = {
    doTests(boxId, STANDARD_MIN, STANDARD_MAX, builder, testEmpty = testEmpty)
  }

  def testAccountsMoneyValidationWithMin(boxId: String, minValue: Int, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean = true): Unit = {
    doTests(boxId, minValue, STANDARD_MAX, builder, testEmpty = testEmpty)
  }

  def testAccountsMoneyValidationWithMinMax(boxId: String, minValue: Int, maxValue: Int, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean = true): Unit = {
    doTests(boxId, minValue, maxValue, builder, testEmpty = testEmpty)
  }

  private def doTests(boxId: String, minValue: Int, maxValue: Int, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean): Unit = {
    setUpMocks()
    s"$boxId" should {
      "be valid when minimum" in {
        builder(Some(minValue)).validate(boxRetriever) shouldBe empty
      }
      "be valid when empty" in {
        if(testEmpty) {
          builder(None).validate(boxRetriever) shouldBe empty
        } else {
          assert(true)
        }
      }
      "be valid when greater then min" in {
        builder(Some(minValue + 1)).validate(boxRetriever) shouldBe empty
      }
      "be valid when positive but equals upper limit" in {
        builder(Some(maxValue)).validate(boxRetriever) shouldBe empty
      }
      "fail validation when less then min lower limit" in {
        builder(Some(minValue - 1)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.below.min", Some(Seq(minValue.toString, maxValue.toString))))
      }
      "fail validation when positive but above upper limit" in {
        builder(Some(maxValue + 1)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.above.max", Some(Seq(minValue.toString, maxValue.toString))))
      }
    }
  }

}
