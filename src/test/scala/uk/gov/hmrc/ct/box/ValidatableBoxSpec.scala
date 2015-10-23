/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.ct.box

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

class ValidatableBoxSpec  extends WordSpec with MockitoSugar  with Matchers with ValidatableBox[BoxRetriever]{

  override def validate(boxRetriever: BoxRetriever): Set[CtValidation] = ???

  "validateBooleanAsMandatory" should {
    "return error if None" in {
       validateBooleanAsMandatory("testBox", testOptBooleanBox(None)) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.required"))
    }

    "return no errors if any value present" in {
      validateBooleanAsMandatory("testBox", testOptBooleanBox(Some(true))) shouldBe Set()
      validateBooleanAsMandatory("testBox", testOptBooleanBox(Some(false))) shouldBe Set()
    }
  }

  "validateIntegerAsMandatory" should {
    "return error if None" in {
      validateIntegerAsMandatory("testBox", testOptIntegerBox(None)) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.required"))
    }

    "return no errors if any value present" in {
      validateIntegerAsMandatory("testBox", testOptIntegerBox(Some(0))) shouldBe Set()
    }
  }

  "validateStringAsMandatory" should {
    "return error if None" in {
      validateStringAsMandatory("testBox", testOptStringBox(None)) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.required"))
    }

    "return no errors if any value present" in {
      validateStringAsMandatory("testBox", testOptStringBox(Some("wibble"))) shouldBe Set()
    }
  }

  "validateStringAsBlank" should {
    "return error if any value is present" in {
      validateStringAsBlank("testBox", testOptStringBox(Some("wibble"))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.nonBlankValue"))
    }

    "return no errors if None" in {
      validateStringAsBlank("testBox", testOptStringBox(None)) shouldBe Set()
    }

    "return no errors if empty string passed" in {
      validateStringAsBlank("testBox", testOptStringBox(Some(""))) shouldBe Set()
    }
  }

  "validateDateAsMandatory" should {
    "return error if None" in {
      validateDateAsMandatory("testBox", testOptDateBox(None)) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.required"))
    }

    "return no errors if any value present" in {
      validateDateAsMandatory("testBox", testOptDateBox(Some(new LocalDate))) shouldBe Set()
    }
  }

  "validateDateAsBlank" should {
    "return error if not blank" in {
      validateDateAsBlank("testBox", testOptDateBox(Some(new LocalDate()))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.nonBlankValue"))
    }

    "return no errors if blank" in {
      validateDateAsBlank("testBox", testOptDateBox(None)) shouldBe Set()
    }
  }
  
  "validateNumberRange" should {
    "return error if number too small" in {
      validateIntegerRange("testBox", testOptIntegerBox(Some(0)), min = 1, max = 2) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.outOfRange", Some(Seq("1","2"))))
    }

    "return error if number too large" in {
      validateIntegerRange("testBox", testOptIntegerBox(Some(3)), min = 1, max = 2) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.outOfRange", Some(Seq("1","2"))))
    }


    "return no errors if within range" in {
      validateIntegerRange("testBox", testOptIntegerBox(Some(0)), min = 0, max =2) shouldBe Set()
    }

    "return no errors if no value present" in {
      validateIntegerRange("testBox", testOptIntegerBox(None), min = 0, max =2) shouldBe Set()
    }
  }

  "validateOptionalStringByRegex" should {
    "return error if it does not match" in {
      validateOptionalStringByRegex("testBox", testOptStringBox(Some("1234567")), regexString) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.regexFailure"))
    }

    "return no errors if it matches" in {
      validateOptionalStringByRegex("testBox", testOptStringBox(Some("12345678")), regexString) shouldBe Set()
    }

    "return no errors if no value set" in {
      validateOptionalStringByRegex("testBox", testOptStringBox(None), regexString) shouldBe Set()
    }

    "return no errors if empty string" in {
      validateOptionalStringByRegex("testBox", testOptStringBox(Some("")), regexString) shouldBe Set()
    }
  }

  "validateStringByLength" should {
    "pass if in range #1" in {
      validateStringByLength("testBox", testStringBox("1234567"), 7,8) shouldBe Set()
    }

    "pass if in range #2" in {
      validateStringByLength("testBox", testStringBox("12345678"), 7,8) shouldBe Set()
    }

    "pass if empty" in {
      validateStringByLength("testBox", testStringBox(""), 7,8) shouldBe Set()
    }

    "return error if too short" in {
      validateStringByLength("testBox", testStringBox("123456"), 7,8) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.text.sizeRange", Some(Seq("7","8"))))
    }

    "return error if too long" in {
      validateStringByLength("testBox", testStringBox("123456789"), 7,8) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.text.sizeRange", Some(Seq("7","8"))))
    }
  }

  "validateAllFilledOrEmptyStrings" should {
    "pass if all strings non-empty" in {
      validateAllFilledOrEmptyStrings("testBox", Set(testStringBox("something"),testStringBox("something"))) shouldBe Set()
    }

    "pass if all string empty" in {
      validateAllFilledOrEmptyStrings("testBox", Set(testStringBox(""),testStringBox(""))) shouldBe Set()
    }

    "return error if mix of empty and non-empty" in {
      validateAllFilledOrEmptyStrings("testBox", Set(testStringBox("something"),testStringBox(""))) shouldBe Set(CtValidation(Some("testBox"),"error.testBox.allornone"))
    }
  }

  "validateAllFilledOrEmptyStringsForBankDetails" should {
    "return error if mixing empty and non-empty" in {
      val mockBoxRetriever = mock[CT600BoxRetriever]
      when(mockBoxRetriever.retrieveB920()).thenReturn(B920(""))
      when(mockBoxRetriever.retrieveB925()).thenReturn(B925(""))
      when(mockBoxRetriever.retrieveB930()).thenReturn(B930(""))
      when(mockBoxRetriever.retrieveB935()).thenReturn(B935("something"))

      validateAllFilledOrEmptyStringsForBankDetails(mockBoxRetriever, "testBox") shouldBe Set(CtValidation(Some("testBox"),"error.testBox.allornone"))

      verify(mockBoxRetriever).retrieveB920()
      verify(mockBoxRetriever).retrieveB925()
      verify(mockBoxRetriever).retrieveB930()
      verify(mockBoxRetriever).retrieveB935()
      verifyNoMoreInteractions(mockBoxRetriever)
    }
  }

  "validateStringAsMandatoryIfPAYEEQ1False" should {
    "return is-required error if PAYEEQ1 is false" in {
      val mockBoxRetriever = mock[CT600BoxRetriever]
      when(mockBoxRetriever.retrievePAYEEQ1()).thenReturn(PAYEEQ1(Some(false)))

      validateStringAsMandatoryIfPAYEEQ1False(mockBoxRetriever, "testBox",testOptStringBox(None)) shouldBe Set(CtValidation(Some("testBox"),"error.testBox.required"))

      verify(mockBoxRetriever).retrievePAYEEQ1()
      verifyNoMoreInteractions(mockBoxRetriever)
    }

    "do not return is-required error if PAYEEQ1 is true" in {
      val mockBoxRetriever = mock[CT600BoxRetriever]
      when(mockBoxRetriever.retrievePAYEEQ1()).thenReturn(PAYEEQ1(Some(true)))

      validateStringAsMandatoryIfPAYEEQ1False(mockBoxRetriever, "testBox",testOptStringBox(None)) shouldBe Set()

      verify(mockBoxRetriever).retrievePAYEEQ1()
      verifyNoMoreInteractions(mockBoxRetriever)
    }
  }

  "validateAsMandatory" should {
    "return error if None" in {
      validateAsMandatory( testOptStringBox(None)) shouldBe Set(CtValidation(Some("testOptStringBox"), "error.testOptStringBox.required"))
    }

    "return no errors if any value present" in {
      validateAsMandatory(testOptStringBox(Some("This is a string."))) shouldBe Set()
    }
  }

  "validatePositiveInteger" should {
    "return error if number is negative" in {
      validatePositiveInteger(testOptIntegerBox(Some(-1))) shouldBe Set(CtValidation(Some("testOptIntegerBox"), "error.testOptIntegerBox.mustBePositive"))
    }

    "return no errors if positive" in {
      validatePositiveInteger(testOptIntegerBox(Some(0))) shouldBe Set()
      validatePositiveInteger(testOptIntegerBox(Some(1))) shouldBe Set()
    }

    "return no errors if no value present" in {
      validatePositiveInteger(testOptIntegerBox(None)) shouldBe Set()
    }
  }


  case class testOptBooleanBox(value: Option[Boolean]) extends CtBoxIdentifier("testBox") with CtOptionalBoolean{}
  case class testOptIntegerBox(value: Option[Int]) extends CtBoxIdentifier("testBox") with CtOptionalInteger{}
  case class testOptStringBox(value: Option[String]) extends CtBoxIdentifier("testBox") with CtOptionalString{}
  case class testStringBox(value: String) extends CtBoxIdentifier("testBox") with CtString{}
  case class testOptDateBox(value: Option[LocalDate]) extends CtBoxIdentifier("testBox") with CtOptionalDate{}

  val regexString = "[0-9]{8}" // numbers only and 8 numbers long
}


