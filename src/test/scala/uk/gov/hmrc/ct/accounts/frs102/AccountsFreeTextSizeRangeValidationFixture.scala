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

package uk.gov.hmrc.ct.accounts.frs102

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountsFreeTextSizeRangeValidationFixture extends WordSpec with Matchers with MockitoSugar {

  self: MockFrs102AccountsRetriever =>

  //This can be overridden if mock box retriever calls need to be made
  def setUpMocks(): Unit = Unit

  def testAccountsCharacterSizeRangeValidation(boxId: String, lowerLimit: Int , upperLimit: Int, builder: (Option[String]) => ValidatableBox[Frs102AccountsBoxRetriever]): Unit = {
    setUpMocks()
    "pass validation when empty" in {
      builder(None).validate(boxRetriever) shouldBe Set.empty
    }

    testMandatoryAccountsCharacterSizeRangeValidation(boxId, lowerLimit, upperLimit,builder)
  }

  def testMandatoryAccountsCharacterSizeRangeValidation(boxId: String, lowerLimit: Int , upperLimit: Int, builder: (Option[String]) => ValidatableBox[Frs102AccountsBoxRetriever]): Unit = {

    "pass validation when empty string" in {
      builder(Some("")).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation with valid string value" in {
      builder(Some("testing this like crazy")).validate(boxRetriever) shouldBe Set.empty
    }

    s"pass validation when string is $upperLimit characters long" in {
      val string = "a" * upperLimit
      builder(Some(string)).validate(boxRetriever) shouldBe Set.empty
    }

    s"fail validation when string is longer than $upperLimit characters long" in {
      val string = "a" * (upperLimit + 1)
      builder(Some(string)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(s"$lowerLimit", s"$upperLimit"))))
    }

    s"fail validation when string is shorter than $lowerLimit characters long" in {
      if(lowerLimit > 0) {
        val string = "a" * (lowerLimit - 1)
        builder(Some(string)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(s"$lowerLimit", s"$upperLimit"))))
      }
    }
  }

  def testAccountsCoHoTextFieldValidation(boxId: String, builder: (Option[String]) => ValidatableBox[Frs102AccountsBoxRetriever]): Unit = {
    setUpMocks()
    "fail validation if invalid characters" in {
      builder(Some("^ §")).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure", Some(List("^  §"))))
    }
  }

  def testAccountsCoHoNameFieldValidation(boxId: String, builder: (Option[String]) => ValidatableBox[Frs102AccountsBoxRetriever]): Unit = {
    setUpMocks()
    "fail validation if invalid characters" in {
      builder(Some("^ §")).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure", None))
    }
  }
}
