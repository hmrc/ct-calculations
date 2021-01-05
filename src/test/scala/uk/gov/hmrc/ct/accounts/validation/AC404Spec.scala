/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.validation

import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC402, AC404}
import uk.gov.hmrc.ct.box.CtValidation

class AC404Spec extends WordSpec with Matchers with MockitoSugar{

  val boxRetriever = mock[AccountsBoxRetriever]

  "AC404 validation" should {
    "show error if AC404 is larger than AC401" in {
      when(boxRetriever.ac402())thenReturn AC402(Some(1000))
      val validationResult = AC404(10001).validate(boxRetriever)

      validationResult shouldBe Set(CtValidation(Some("AC404"),"error.AC404.exceeds.AC402"))
    }

    "show error if AC404 isnt present when AC402 exists" in {
      when(boxRetriever.ac402())thenReturn AC402(Some(1))
      val validationResult = AC404(None).validate(boxRetriever)

      validationResult shouldBe Set(CtValidation(Some("AC404"),"error.AC404.required"))
    }
  }

}
