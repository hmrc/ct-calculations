/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.utils.UnitSpec

trait AccountsIntegerValidationFixture[T <: AccountsBoxRetriever] extends WordSpec with MockitoSugar with Matchers {

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
