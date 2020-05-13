/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC299ASpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter
  with MockFrs102AccountsRetriever with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  testTextFieldValidation("AC299A", AC299A, testUpperLimit = Some(StandardCohoNameFieldLimit), testMandatory = Some(true))
  testTextFieldIllegalCharactersValidation("AC299A", AC299A)

  "AC299A" should {
    "be mandatory" in {
      AC299A(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC299A"), "error.AC299A.required", None))
    }
  }
}
