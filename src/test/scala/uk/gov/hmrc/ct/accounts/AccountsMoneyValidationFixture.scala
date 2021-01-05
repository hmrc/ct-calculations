/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountsMoneyValidationFixture[T <: AccountsBoxRetriever] extends WordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  val STANDARD_MIN = -99999999
  val STANDARD_MAX = 99999999

  def setUpMocks(): Unit = {
    when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate())))
  }

  def testAccountsMoneyValidation(boxId: String, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean = true): Unit = {
    doTests(boxId, STANDARD_MIN, STANDARD_MAX, builder, testEmpty = testEmpty)
  }

  //Used to test cases where the passing the minimum would be covered by another error (e.g. non-negative validation)
  def testAccountsMoneyValidationWithMin(boxId: String, minValue: Int, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean = true, testMin: Boolean = true): Unit = {
    doTests(boxId, minValue, STANDARD_MAX, builder, testEmpty = testEmpty, testMin)
  }

  def testAccountsMoneyValidationWithMinMax(boxId: String, minValue: Int, maxValue: Int, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean = true): Unit = {
    doTests(boxId, minValue, maxValue, builder, testEmpty = testEmpty)
  }

  private def doTests(boxId: String, minValue: Int, maxValue: Int, builder: (Option[Int]) => ValidatableBox[T], testEmpty: Boolean, testMin: Boolean = true): Unit = {
    setUpMocks()
    s"$boxId" should {
      "be valid when minimum" in {
        builder(Some(minValue)).validate(boxRetriever) shouldBe empty
      }
      "be valid when empty" in {
        if (testEmpty) {
          builder(None).validate(boxRetriever) shouldBe empty
        } else {
          assert(true)
        }
      }
      "be valid when greater then min" in {
        builder(Some(minValue + 1)).validate(boxRetriever) shouldBe empty
      }
      "be valid when positive but equals upper limit" in {
        builder(Some(maxValue)).validate(boxRetriever) shouldBe empty
      }

      if (testMin) {
        "fail validation when less then min lower limit" in {
          builder(Some(minValue - 1)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.below.min", Some(Seq(minValue.toString, maxValue.toString))))
        }
      }

      "fail validation when positive but above upper limit" in {
        builder(Some(maxValue + 1)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some(boxId), s"error.$boxId.above.max", Some(Seq(minValue.toString, maxValue.toString))))
      }
    }
  }
}
