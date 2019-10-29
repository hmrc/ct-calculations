package uk.gov.hmrc.ct.accounts

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountsIntegerValidationFixture[T <: AccountsBoxRetriever] extends WordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  def setUpMocks(): Unit = Unit

  def testIntegerFieldValidation(boxId: String, builder: Option[Int] => ValidatableBox[T], testLowerLimit: Option[Int] = None, testUpperLimit: Option[Int] = None, testMandatory: Option[Boolean] = Some(false)) = {
    if (testMandatory.contains(true)) {
      "fail validation when empty integer" in {
        builder(None).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.required", None))
      }
    } else if (testMandatory.contains(false)) {
      "pass validation when empty integer" in {
        builder(None).validate(boxRetriever) shouldBe Set.empty
      }
    } else {
      //'None' disables validation on empty strings, required to avoid failures in cases when a box being mandatory depends on the value of another box.
    }

    "pass validation with valid integer value" in {
      builder(Some(50)).validate(boxRetriever) shouldBe Set.empty
    }

    if(testLowerLimit.isDefined && testUpperLimit.isDefined) {
      val lowerLimit = testLowerLimit.get
      val upperLimit = testUpperLimit.get

      s"pass validation when integer is $upperLimit characters long" in {
        builder(Some(12345)).validate(boxRetriever) shouldBe Set.empty
      }

      s"fail validation when integer is longer than $upperLimit characters long" in {
        builder(Some(123456)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(s"$lowerLimit", s"$upperLimit"))))
      }

      s"fail validation when integer is shorter than $lowerLimit characters long" in {
        if(lowerLimit > 1) {

          builder(Some(12)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(s"$lowerLimit", s"$upperLimit"))))
        }
      }
    }

    if(testLowerLimit.isEmpty && testUpperLimit.isDefined) {
      val upperLimit = testUpperLimit.get

      s"pass validation when integer is $upperLimit characters long" in {
        builder(Some(12345)).validate(boxRetriever) shouldBe Set.empty
      }

      s"fail validation when integer is longer than $upperLimit characters long" in {
        builder(Some(123456)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.max.length", Some(Seq(f"$upperLimit%,d"))))
      }
    }
  }
}
