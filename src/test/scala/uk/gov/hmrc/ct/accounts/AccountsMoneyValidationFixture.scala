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

package uk.gov.hmrc.ct.accounts

import java.time.LocalDate
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import ValidatableBox._
trait AccountsMoneyValidationFixture[T <: AccountsBoxRetriever] extends AnyWordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  val STANDARD_MIN = -99999999
  val STANDARD_MAX = 99999999

  def setUpMocks(): Unit = {
    when(boxRetriever.ac205()).thenReturn(AC205(Some(LocalDate.now())))
  }

  def testAccountsMoneyValidation(boxId: String, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean = true): Unit = {
    doTests(boxId, STANDARD_MIN, STANDARD_MAX, builder, testEmpty = testEmpty)
  }

  //Used to test cases where the passing the minimum would be covered by another error (e.g. non-negative validation)
  def testAccountsMoneyValidationWithMin(boxId: String, minValue: Int, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean = true, testMin: Boolean = true): Unit = {
    doTests(boxId, minValue, STANDARD_MAX, builder, testEmpty = testEmpty, testMin)
  }

  def testAccountsMoneyValidationWithMinMax(boxId: String, minValue: Int, maxValue: Int, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean = true): Unit = {
    doTests(boxId, minValue, maxValue, builder, testEmpty = testEmpty)
  }

  private def doTests(boxId: String, minValue: Int, maxValue: Int, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean, testMin: Boolean = true): Unit = {
    setUpMocks()
    s"$boxId" should {
      "be valid when minimum" in {
       builder(Some(minValue)).validate(boxRetriever) shouldBe empty
      }
      "be valid when empty" in {
        if (testEmpty) {
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

       val errorArguments = Some(Seq(commaForThousands(minValue), commaForThousands(maxValue)))

      if (testMin) {
        "fail validation when less then min lower limit" in {
          builder(Some(minValue - 1)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.below.min", errorArguments))
        }
      }

      "fail validation when positive but above upper limit" in {
        builder(Some(maxValue + 1)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.above.max", errorArguments))
      }
    }
  }
}
