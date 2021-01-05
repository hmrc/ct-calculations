/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.validation

import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC403Spec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[AccountsBoxRetriever]

  "AC403 validation" should {
    "show error if AC403 is larger than AC401" in {
      when(boxRetriever.ac401())thenReturn AC401(Some(1000))
      val validationResult = AC403(10001).validate(boxRetriever)

      validationResult shouldBe Set(CtValidation(Some("AC403"),"error.AC403.exceeds.AC401"))
    }

    "show error if AC403 isnt present when AC401 exists" in {
      when(boxRetriever.ac401())thenReturn AC401(Some(1))
      val validationResult = AC403(None).validate(boxRetriever)

      validationResult shouldBe Set(CtValidation(Some("AC403"),"error.AC403.required"))
    }
  }
}
