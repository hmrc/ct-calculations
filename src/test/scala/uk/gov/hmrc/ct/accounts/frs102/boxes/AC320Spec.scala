/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC320Spec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac320A()).thenReturn(AC320A(Some("text")))
  }

  "AC320 validate" should {
    "return errors when AC320 is empty" in {
      val mockBoxRetriever = mock[Frs102AccountsBoxRetriever]

      AC320(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC320"), "error.AC320.required"))
    }
  }

  "return value when AC320 is not empty" in {
    val mockBoxRetriever = mock[Frs102AccountsBoxRetriever]

    AC320(Some(true)).value shouldBe Some(true)
  }
}
