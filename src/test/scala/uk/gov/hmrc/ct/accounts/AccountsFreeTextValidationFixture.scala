/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountsFreeTextValidationFixture[T <: AccountsBoxRetriever] extends WordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  //This can be overridden if mock box retriever calls need to be made
  def setUpMocks(): Unit = Unit

  def testTextFieldValidation(boxId: String, builder: (Option[String]) => ValidatableBox[T], testLowerLimit: Option[Int] = None, testUpperLimit: Option[Int] = None, testMandatory: Option[Boolean] = Some(false)) = {

    if(testMandatory == Some(true)) {
      "fail validation when empty string" in {
        builder(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.required", None))
      }
    } else if(testMandatory == Some(false)) {
      "pass validation when empty string" in {
        builder(None).validate(boxRetriever) shouldBe Set.empty
      }
    } else {
      //'None' disables validation on empty strings, required to avoid failures in cases when a box being mandatory depends on the value of another box.
    }

    "pass validation with valid string value" in {
      builder(Some("testing this like crazy")).validate(boxRetriever) shouldBe Set.empty
    }

    if(testLowerLimit.isDefined && testUpperLimit.isDefined) {
      val lowerLimit = testLowerLimit.get
      val upperLimit = testUpperLimit.get

      s"pass validation when string is $upperLimit characters long" in {
        val string = "a" * upperLimit
        builder(Some(string)).validate(boxRetriever) shouldBe Set.empty
      }

      s"fail validation when string is longer than $upperLimit characters long" in {
        val string = "a" * (upperLimit + 1)
        builder(Some(string)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(s"$lowerLimit", s"$upperLimit"))))
      }

      s"fail validation when string is shorter than $lowerLimit characters long" in {
        if(lowerLimit > 1) {
          val string = "a" * (lowerLimit - 1)
          builder(Some(string)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(s"$lowerLimit", s"$upperLimit"))))
        }
      }
    }

    if(!testLowerLimit.isDefined && testUpperLimit.isDefined) {
      val upperLimit = testUpperLimit.get

      s"pass validation when string is $upperLimit characters long" in {
        val string = "a" * upperLimit
        builder(Some(string)).validate(boxRetriever) shouldBe Set.empty
      }

      s"fail validation when string is longer than $upperLimit characters long" in {
        val string = "a" * (upperLimit + 1)
        builder(Some(string)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.max.length", Some(Seq(f"$upperLimit%,d"))))
      }
    }

  }

  def testTextFieldIllegalCharacterValidationReturnsIllegalCharacters(boxId: String, builder: (Option[String]) => ValidatableBox[T]): Unit = {
    setUpMocks()
    "fail validation if invalid characters" in {
      builder(Some("^ §")).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure", Some(List("^  §"))))
    }
  }

  def testTextFieldIllegalCharactersValidation(boxId: String, builder: (Option[String]) => ValidatableBox[T]): Unit = {
    setUpMocks()
    "fail validation if invalid characters" in {
      builder(Some("^ §")).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure", None))
    }
  }
}
