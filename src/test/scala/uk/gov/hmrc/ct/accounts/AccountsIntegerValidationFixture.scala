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
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountsIntegerValidationFixture[T <: AccountsBoxRetriever] extends AnyWordSpec with MockitoSugar with Matchers {

  def boxRetriever: T

  private val mandatoryNotesStartDate: LocalDate = LocalDate.parse("2017-01-01")
  private val previousPeriodOfAccounts:  AC205 = AC205(Some(LocalDate.now()))

  def setUpMocks(): Unit = {
    when(boxRetriever.ac205()) thenReturn previousPeriodOfAccounts
    when(boxRetriever.ac3()) thenReturn AC3(mandatoryNotesStartDate)
  }


  def testIntegerFieldValidation[S](boxId: String, builder: Option[Int] => ValidatableBox[T], testLowerLimit: Option[Int] = None, testUpperLimit: Option[Int] = None, testMandatory: Option[Boolean] = Some(false)): Unit = {
    if (testMandatory.contains(true)) {
      "fail validation when empty integer" in {
        setUpMocks()
        builder(None).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.required", None))
      }
    } else if (testMandatory.contains(false)) {
      "pass validation when empty integer" in {
        builder(None).validate(boxRetriever) shouldBe Set.empty
      }
    } else {
      //'None' disables validation on empty strings, required to avoid failures in cases when a box being mandatory depends on the value of another box.
    }


    if (testLowerLimit.isDefined && testUpperLimit.isDefined) {
      val lowerLimit = testLowerLimit.get
      val lowerLimitWithCommas = f"$lowerLimit%,d"
      val upperLimit = testUpperLimit.get
      val upperLimitWithCommas = f"$upperLimit%,d"

      s"pass validation when integer is the same as the $upperLimit" in {
        builder(Some(upperLimit)).validate(boxRetriever) shouldBe Set.empty
      }

      s"fail validation when integer is bigger than $upperLimit" in {
       setUpMocks()
        builder(Some(upperLimit + 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.outOfRange", Some(Seq(s"$lowerLimitWithCommas", s"$upperLimitWithCommas"))))
      }

      if (lowerLimit > 0) {
      s"fail validation when integer is lower than $lowerLimit characters long" in {
         builder(Some(lowerLimit - 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.outOfRange", Some(Seq(s"$lowerLimitWithCommas", s"$upperLimitWithCommas"))))
       }
      }

      if (testLowerLimit.isEmpty && testUpperLimit.isDefined) {
        val upperLimit = testUpperLimit.get
        val upperLimitWithCommas = f"$upperLimit%,d"
        s"pass validation when integer is the same as the $upperLimit" in {
          builder(Some(upperLimit)).validate(boxRetriever) shouldBe Set.empty
        }

        s"fail validation when integer is bigger than $upperLimit" in {
          builder(Some(upperLimit + 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.max.length", Some(Seq(upperLimitWithCommas))))
        }
      }
    }
  }
}
