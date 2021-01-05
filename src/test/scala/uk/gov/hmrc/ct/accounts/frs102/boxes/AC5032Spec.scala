/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC5032Spec extends WordSpec with MockitoSugar with Matchers with MockFrs102AccountsRetriever with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac32()).thenReturn(AC32(Some(4)))
    when(boxRetriever.ac33()).thenReturn(AC33(Some(4)))
  }

  testTextFieldValidation("AC5032", AC5032, testUpperLimit = Some(StandardCohoTextFieldLimit))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC5032", AC5032)

  "AC5032" should {

    "pass validation when populated and AC32 is empty" in {
      when(boxRetriever.ac32()).thenReturn(AC32(None))
      when(boxRetriever.ac33()).thenReturn(AC33(Some(4)))
      AC5032(Some("testing")).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation when populated and AC33 is empty" in {
      when(boxRetriever.ac32()).thenReturn(AC32(Some(4)))
      when(boxRetriever.ac33()).thenReturn(AC33(None))
      AC5032(Some("testing")).validate(boxRetriever) shouldBe Set.empty
    }

    "fail validation when populated and AC32 and AC33 are empty" in {
      when(boxRetriever.ac32()).thenReturn(AC32(None))
      when(boxRetriever.ac33()).thenReturn(AC33(None))
      AC5032(Some("testing")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5032"), "error.AC5032.cannot.exist"))
    }

  }
}
