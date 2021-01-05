/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.approval.accountsApproval.accountsApproval

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.approval.boxes.AC8091
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC8091Spec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {


  "AC8091 validate" should {
    "return errors when AC8091 is empty" in {
      val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever]

      AC8091(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8091"), "error.AC8091.required"))
    }

    "return errors when AC8091 is false" in {
      val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever]

      AC8091(Some(false)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8091"), "error.AC8091.required"))
    }

    "return value when AC8091 is true" in {
      val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever]

      AC8091(Some(true)).value shouldBe Some(true)
    }
  }
}
