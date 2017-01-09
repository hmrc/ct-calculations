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

package uk.gov.hmrc.ct.box

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v3._
import uk.gov.hmrc.ct.ct600.v3.retriever.{CT600BoxRetriever, RepaymentsBoxRetriever}
import uk.gov.hmrc.ct.domain.ValidationConstants._
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.box.ValidatableBox._


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
      validateDateAsBlank("testBox", testOptDateBox(Some(DateHelper.now()))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.nonBlankValue"))
    }

    "return no errors if blank" in {
      validateDateAsBlank("testBox", testOptDateBox(None)) shouldBe Set()
    }
  }

  "validateDateAsBefore" should {
    "return error if date is after" in {
      validateDateAsBefore("testBox", testOptDateBox(Some(new LocalDate("2013-01-01"))), new LocalDate("2012-12-31")) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.not.before"))
    }
    "return error if date is the same" in {
      validateDateAsBefore("testBox", testOptDateBox(Some(new LocalDate("2013-12-31"))), new LocalDate("2012-12-31")) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.not.before"))
    }
    "return no errors if date is before" in {
      validateDateAsBefore("testBox", testOptDateBox(Some(new LocalDate("2012-12-30"))), new LocalDate("2012-12-31")) shouldBe Set()
    }
  }

  "validateDateAsAfter" should {
    "return error if date is before" in {
      validateDateAsAfter("testBox", testOptDateBox(Some(new LocalDate("2012-12-30"))), new LocalDate("2012-12-31")) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.not.after"))
    }
    "return error if date is the same" in {
      validateDateAsAfter("testBox", testOptDateBox(Some(new LocalDate("2012-12-31"))), new LocalDate("2012-12-31")) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.not.after"))
    }
    "return no errors if date is after" in {
      validateDateAsAfter("testBox", testOptDateBox(Some(new LocalDate("2013-01-01"))), new LocalDate("2012-12-31")) shouldBe Set()
    }
  }

  "validateDateBetweenInclusive" should {
    val minDate = new LocalDate("2012-12-31")
    val maxDate = new LocalDate("2013-12-31")

    "return error if date is before start date" in {
      validateDateAsBetweenInclusive("testBox", testOptDateBox(Some(new LocalDate("2012-12-30"))), minDate, maxDate) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.not.betweenInclusive", Some(Seq(toErrorArgsFormat(minDate), toErrorArgsFormat(maxDate)))))
    }
    "return no errors if date is on start date" in {
      validateDateAsBetweenInclusive("testBox", testOptDateBox(Some(new LocalDate("2012-12-31"))), minDate, maxDate) shouldBe Set()
    }
    "return error if date is after end date" in {
      validateDateAsBetweenInclusive("testBox", testOptDateBox(Some(new LocalDate("2014-01-01"))), minDate, maxDate) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.not.betweenInclusive", Some(Seq(toErrorArgsFormat(minDate), toErrorArgsFormat(maxDate)))))
    }
    "return no errors if date is on end date" in {
      validateDateAsBetweenInclusive("testBox", testOptDateBox(Some(new LocalDate("2013-12-31"))), minDate, maxDate) shouldBe Set()
    }
  }

  "validateIntegerAsBlank" should {
    "return no errors if no value present" in {
      validateIntegerAsBlank("testBox", testOptIntegerBox(None)) shouldBe Set()
    }

    "return validation error if non empty" in {
      validateIntegerAsBlank("testBox", testOptIntegerBox(Some(0))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.nonBlankValue"))
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

  "validateCoHoOptionalTextField" should {
    "return unique errors if it does not match" in {
      validateCoHoStringReturnIllegalChars("testBox", testOptStringBox(Some("^ ^^  aa § 333"))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.regexFailure", Some(List("^  §"))))
    }

    "return no errors if it matches character set 0" in {
      validateCoHoStringReturnIllegalChars("testBox", testOptStringBox(Some("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"))) shouldBe Set()
    }

    "return no errors if it matches character set 1" in {
      validateCoHoStringReturnIllegalChars("testBox", testOptStringBox(Some("0123456789"))) shouldBe Set()
    }

    "return no errors if it matches character set 2" in {
      validateCoHoStringReturnIllegalChars("testBox", testOptStringBox(Some("&@£$€¥.,:;"))) shouldBe Set()
    }

    "return no errors if it matches character set 3" in {
      validateCoHoStringReturnIllegalChars("testBox", testOptStringBox(Some("-'()[]{}"))) shouldBe Set()
    }

    "return no errors if it matches character set 4" in {
      validateCoHoStringReturnIllegalChars("testBox", testOptStringBox(Some("?/\\#%+\\r=*"))) shouldBe Set()
    }

    "return no errors if it matches character set 5" in {
      validateCoHoStringReturnIllegalChars("testBox", testOptStringBox(Some("<>!»«\"“”   ‘ ’ ’    "))) shouldBe Set()
    }

    "return no errors if no value set" in {
      validateCoHoStringReturnIllegalChars("testBox", testOptStringBox(None)) shouldBe Set()
    }

    "return no errors if empty string" in {
      validateCoHoStringReturnIllegalChars("testBox", testOptStringBox(Some(""))) shouldBe Set()
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

  "validateStringMaxLength" should {
    "pass if less than max" in {
      validateStringMaxLength("testBox", "1234567", 8) shouldBe Set()
    }

    "pass if equal to max" in {
      validateStringMaxLength("testBox", "12345678", 8) shouldBe Set()
    }

    "pass if empty" in {
      validateStringMaxLength("testBox", "", 8) shouldBe Set()
    }

    "return error if too long with thousands formatted as commas" in {
      validateStringMaxLength("testBox", "1" * 20001, StandardCohoTextFieldLimit) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.max.length", Some(Seq("20,000"))))
    }
  }

  "validateOptionalStringByLength" should {
    "pass if none" in {
      validateOptionalStringByLength("testBox", testOptStringBox(None), 7, 8) shouldBe Set()
    }
    "pass if in range #1" in {
      validateOptionalStringByLength("testBox", testOptStringBox(Some("1234567")), 7, 8) shouldBe Set()
    }

    "pass if in range #2" in {
      validateOptionalStringByLength("testBox", testOptStringBox(Some("12345678")), 7, 8) shouldBe Set()
    }

    "pass if empty" in {
      validateOptionalStringByLength("testBox", testOptStringBox(Some("")), 7, 8) shouldBe Set()
    }

    "return error if too short" in {
      validateOptionalStringByLength("testBox", testOptStringBox(Some("123456")), 7, 8) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.text.sizeRange", Some(Seq("7","8"))))
    }

    "return error if too long" in {
      validateOptionalStringByLength("testBox", testOptStringBox(Some("123456789")), 7, 8) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.text.sizeRange", Some(Seq("7","8"))))
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
      val mockBoxRetriever = mock[RepaymentsBoxRetriever]
      when(mockBoxRetriever.b920()).thenReturn(B920(""))
      when(mockBoxRetriever.b925()).thenReturn(B925(""))
      when(mockBoxRetriever.b930()).thenReturn(B930(""))
      when(mockBoxRetriever.b935()).thenReturn(B935("something"))

      validateAllFilledOrEmptyStringsForBankDetails(mockBoxRetriever, "testBox") shouldBe Set(CtValidation(Some("testBox"),"error.testBox.allornone"))

      verify(mockBoxRetriever).b920()
      verify(mockBoxRetriever).b925()
      verify(mockBoxRetriever).b930()
      verify(mockBoxRetriever).b935()
      verifyNoMoreInteractions(mockBoxRetriever)
    }
  }

  "validateStringAsMandatoryIfPAYEEQ1False" should {
    "return is-required error if PAYEEQ1 is false" in {
      val mockBoxRetriever = mock[RepaymentsBoxRetriever]
      when(mockBoxRetriever.payeeQ1()).thenReturn(PAYEEQ1(Some(false)))

      validateStringAsMandatoryIfPAYEEQ1False(mockBoxRetriever, "testBox",testOptStringBox(None)) shouldBe Set(CtValidation(Some("testBox"),"error.testBox.required"))

      verify(mockBoxRetriever).payeeQ1()
      verifyNoMoreInteractions(mockBoxRetriever)
    }

    "do not return is-required error if PAYEEQ1 is true" in {
      val mockBoxRetriever = mock[RepaymentsBoxRetriever]
      when(mockBoxRetriever.payeeQ1()).thenReturn(PAYEEQ1(Some(true)))

      validateStringAsMandatoryIfPAYEEQ1False(mockBoxRetriever, "testBox",testOptStringBox(None)) shouldBe Set()

      verify(mockBoxRetriever).payeeQ1()
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

  "validateZeroOrPositiveBigDecimal" should {
    "return error if number is negative" in {
      validateZeroOrPositiveBigDecimal(testOptBigDecimalBox(Some(BigDecimal(-0.99)))) shouldBe Set(CtValidation(Some("testOptBigDecimalBox"), "error.testOptBigDecimalBox.mustBeZeroOrPositive"))
    }

    "return no errors if positive" in {
      validateZeroOrPositiveBigDecimal(testOptBigDecimalBox(Some(BigDecimal(0)))) shouldBe Set()
      validateZeroOrPositiveBigDecimal(testOptBigDecimalBox(Some(BigDecimal(0.99)))) shouldBe Set()
    }

    "return no errors if no value present" in {
      validateZeroOrPositiveBigDecimal(testOptBigDecimalBox(None)) shouldBe Set()
    }
  }

  "validatePositiveInteger" should {
    "return error if number is negative" in {
      validateZeroOrPositiveInteger(testOptIntegerBox(Some(-1))) shouldBe Set(CtValidation(Some("testOptIntegerBox"), "error.testOptIntegerBox.mustBeZeroOrPositive"))
    }

    "return no errors if positive" in {
      validateZeroOrPositiveInteger(testOptIntegerBox(Some(0))) shouldBe Set()
      validateZeroOrPositiveInteger(testOptIntegerBox(Some(1))) shouldBe Set()
    }

    "return no errors if no value present" in {
      validateZeroOrPositiveInteger(testOptIntegerBox(None)) shouldBe Set()
    }
  }

  "validatePostcode" should {
    "be happy if postcode is within length limits and matches regex" in {
      validatePostcode("testbox", testOptStringBox(Some("NW1 9PP"))) shouldBe Set()
      validatePostcode("testbox", testOptStringBox(Some("NW12 9PP"))) shouldBe Set()
    }
    "fail if postcode is too long" in {
      validatePostcode("testBox", testOptStringBox(Some("NW12 99PP"))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.invalidPostcode"))
      validatePostcode("testBox", testOptStringBox(Some("NW12999PP"))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.invalidPostcode"))
    }
    "fail if the space in the middle is missing ?!??!!" in {
      validatePostcode("testBox", testOptStringBox(Some("NW19PP"))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.invalidPostcode"))
      validatePostcode("testBox", testOptStringBox(Some("NW129PP"))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.invalidPostcode"))
    }
    "fail if postcode contains non standard characters" in {
      validatePostcode("testBox", testOptStringBox(Some("NW1 }9PP"))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.invalidPostcode"))
      validatePostcode("testBox", testOptStringBox(Some("NW1Ë 9PP"))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.invalidPostcode"))
      validatePostcode("testBox", testOptStringBox(Some("Å©W1 9PP"))) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.invalidPostcode"))
    }
  }

  "fail if" should {
    "evaluate and return validation errors if condition is true" in {

      val result = failIf (true) {
        Set(CtValidation(None, "", None))
      }

      result shouldBe Set(CtValidation(None, "", None))
    }

    "return empy set if condition is false" in {

      val result = failIf (false) {
        Set(CtValidation(None, "", None))
      }

      result shouldBe empty
    }

    "pass if" should {
      "return empy set if condition is true" in {

        val result = passIf(true) {
          Set(CtValidation(None, "", None))
        }

        result shouldBe empty
      }

      " evaluate and return validation errors if condition is false" in {

        val result = passIf(false) {
          Set(CtValidation(None, "", None))
        }

        result shouldBe Set(CtValidation(None, "", None))
      }
    }
  }


  case class testOptBooleanBox(value: Option[Boolean]) extends CtBoxIdentifier("testBox") with CtOptionalBoolean{}
  case class testOptIntegerBox(value: Option[Int]) extends CtBoxIdentifier("testBox") with CtOptionalInteger{}
  case class testOptBigDecimalBox(value: Option[BigDecimal]) extends CtBoxIdentifier("testBox") with CtOptionalBigDecimal{}
  case class testOptStringBox(value: Option[String]) extends CtBoxIdentifier("testBox") with CtOptionalString{}
  case class testStringBox(value: String) extends CtBoxIdentifier("testBox") with CtString{}
  case class testOptDateBox(value: Option[LocalDate]) extends CtBoxIdentifier("testBox") with CtOptionalDate{}

  val regexString = "[0-9]{8}" // numbers only and 8 numbers long
}
