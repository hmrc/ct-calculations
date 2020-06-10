/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.validation

import org.mockito.Mockito.when
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC402, AC404}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.utils.UnitSpec

class AC402Spec extends UnitSpec {

  "AC402 validation" should {
    val boxRetriever = mock[AccountsBoxRetriever]
    "not show error messages where AC402 is within limit" in {
      when(boxRetriever.ac404()).thenReturn(AC404(Some(0)))

      AC402(Some(0)).validate(boxRetriever) shouldBe Set.empty[CtValidation]
      AC402(Some(999999)).validate(boxRetriever) shouldBe Set.empty[CtValidation]
    }

    "show correct error messages where AC402 is outside limit" in {
      when(boxRetriever.ac404()).thenReturn(AC404(Some(0)))

      AC402(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC402"),"error.AC402.mustBeZeroOrPositive",None))
      AC402(Some(-0)).validate(boxRetriever) shouldBe Set.empty[CtValidation]
      AC402(Some(1000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC402"),"error.AC402.exceeds.max",Some(List("999999"))))
    }


    "show correct error message when AC402 doesn't need to be present" in{
      when(boxRetriever.ac404()).thenReturn(AC404(Some(1)))

      AC402(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC402"),"error.AC402.required",None))
    }

    "don't show error message when AC402 should be present" in{
      when(boxRetriever.ac404()).thenReturn(AC404(None))

      AC402(None).validate(boxRetriever) shouldBe Set.empty[CtValidation]
    }
  }

}
