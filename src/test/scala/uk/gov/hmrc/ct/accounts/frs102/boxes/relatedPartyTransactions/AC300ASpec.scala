/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC300ASpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter
  with MockFrs102AccountsRetriever with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  testTextFieldValidation("AC300A", AC300A, testUpperLimit = Some(StandardCohoTextFieldLimit), testMandatory = Some(true))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC300A", AC300A)

  "AC300A" should {
    "be mandatory" in {
      AC300A(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC300A"), "error.AC300A.required", None))
    }
  }
}
