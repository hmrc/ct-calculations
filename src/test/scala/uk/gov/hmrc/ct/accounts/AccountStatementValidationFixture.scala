/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

trait AccountStatementValidationFixture[T <: AccountsBoxRetriever] extends WordSpec with Matchers with MockitoSugar {

  def boxRetriever: T

  def setupMocks(): Unit = {}

  def doStatementValidationTests(boxId: String, builder: (Option[Boolean]) => ValidatableBox[T]): Unit = {
    setupMocks()
    s"$boxId" should {

      "validate successfully when true" in {
        builder(Some(true)).validate(boxRetriever) shouldBe Set.empty
      }

      "fail validation if not set" in {
        builder(None).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.required", None))
      }

      "fail validation if false" in {
        builder(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), s"error.$boxId.required", None))
      }
    }
  }
}
