/*
 * Copyright 2019 HM Revenue & Customs
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
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountsIntegerValidationFixture[T <: AccountsBoxRetriever] extends WordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  def setUpMocks(): Unit = Unit

  private val mandatoryNotesStartDate = LocalDate.parse("2017-01-01")

  def testIntegerFieldValidation[S](boxId: String, builder: Option[Int] => ValidatableBox[T], testLowerLimit: Option[Int] = None, testUpperLimit: Option[Int] = None, testMandatory: Option[Boolean] = Some(false), isMandatoryNotes: Boolean) = {


    if (testMandatory.contains(true)) {
      "fail validation when empty integer" in {
        if (isMandatoryNotes) when(boxRetriever.ac3()) thenReturn AC3(mandatoryNotesStartDate)
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
        if (isMandatoryNotes) when(boxRetriever.ac3()) thenReturn AC3(mandatoryNotesStartDate)
        builder(Some(upperLimit + 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.outOfRange", Some(Seq(s"$lowerLimitWithCommas", s"$upperLimitWithCommas"))))
      }

      s"fail validation when integer is lower than $lowerLimit characters long" in {
        builder(Some(lowerLimit - 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.outOfRange", Some(Seq(s"$lowerLimitWithCommas", s"$upperLimitWithCommas"))))
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
