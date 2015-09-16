package uk.gov.hmrc.ct.box

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.retriever.BoxRetriever

class ValidatableBoxSpec  extends WordSpec with Matchers with ValidatableBox[BoxRetriever]{

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
      validateIntegerRange("testBox", testOptIntegerBox(Some(0)), min = 1, max = 2) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.outOfRange"))
    }

    "return error if number too large" in {
      validateIntegerRange("testBox", testOptIntegerBox(Some(3)), min = 1, max = 2) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.outOfRange"))
    }


    "return no errors if within range" in {
      validateIntegerRange("testBox", testOptIntegerBox(Some(0)), min = 0, max =2) shouldBe Set()
    }

    "return no errors if no value present" in {
      validateIntegerRange("testBox", testOptIntegerBox(None), min = 0, max =2) shouldBe Set()
    }
  }

  "validateStringByRegex" should {
    "return error if it does not match" in {
      validateStringByRegex("testBox", testOptStringBox(Some("1234567")), regexString) shouldBe Set(CtValidation(Some("testBox"), "error.testBox.regexFailure"))
    }

    "return no errors if it matches" in {
      validateStringByRegex("testBox", testOptStringBox(Some("12345678")), regexString) shouldBe Set()
    }

    "return no errors if no value set" in {
      validateStringByRegex("testBox", testOptStringBox(None), regexString) shouldBe Set()
    }

    "return no errors if empty string" in {
      validateStringByRegex("testBox", testOptStringBox(Some("")), regexString) shouldBe Set()
    }
  }


  case class testOptBooleanBox(value: Option[Boolean]) extends CtBoxIdentifier("testBox") with CtOptionalBoolean{}
  case class testOptIntegerBox(value: Option[Int]) extends CtBoxIdentifier("testBox") with CtOptionalInteger{}
  case class testOptStringBox(value: Option[String]) extends CtBoxIdentifier("testBox") with CtOptionalString{}
  case class testOptDateBox(value: Option[LocalDate]) extends CtBoxIdentifier("testBox") with CtOptionalDate{}

  val regexString = "[0-9]{8}" // numbers only and 8 numbers long
}
