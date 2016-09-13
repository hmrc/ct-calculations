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

trait AccountsFreeTextValidationFixture extends WordSpec with Matchers with MockitoSugar {

  self: MockAbridgedAccountsRetriever =>

  //This can be overridden if mock box retriever calls need to be made
  def setUpMocks(): Unit = Unit

  def testAccountsCharacterLimitValidation(boxId: String, charLimit: Int, builder: (Option[String]) => ValidatableBox[AbridgedAccountsBoxRetriever]): Unit = {
    setUpMocks()
    "pass validation when empty" in {
      builder(None).validate(boxRetriever) shouldBe Set.empty
    }

    testMandatoryAccountsCharacterLimitValidation(boxId, charLimit ,builder)
  }

  def testMandatoryAccountsCharacterLimitValidation(boxId: String, charLimit: Int, builder: (Option[String]) => ValidatableBox[AbridgedAccountsBoxRetriever]): Unit = {

    "pass validation when empty string" in {
      builder(Some("")).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation with valid string value" in {
      builder(Some("testing this like crazy")).validate(boxRetriever) shouldBe Set.empty
    }

    s"pass validation when string is $charLimit characters long" in {
      val string = "a" * charLimit
      builder(Some(string)).validate(boxRetriever) shouldBe Set.empty
    }

    s"fail validation when string is longer than $charLimit characters long" in {
      val string = "a" * (charLimit + 1)
      builder(Some(string)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.max.length", Some(Seq(f"$charLimit%,d"))))
    }
  }

  def testAccountsCoHoTextFieldValidation(boxId: String, builder: (Option[String]) => ValidatableBox[AbridgedAccountsBoxRetriever]): Unit = {
    setUpMocks()
    "fail validation if invalid characters" in {
      builder(Some("^ §")).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure", Some(List("^, §"))))
    }
  }
}
