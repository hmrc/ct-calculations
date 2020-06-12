/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.validation

import org.mockito.Mockito.when
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.utils.UnitSpec

class AC401Spec extends UnitSpec {

  "AC401 validation" should {
    val boxRetriever = mock[AccountsBoxRetriever]
    "not show error messages where AC401 is within limit" in {
      when(boxRetriever.ac403()).thenReturn(AC403(Some(0)))

      AC401(Some(0)).validate(boxRetriever) shouldBe Set.empty[CtValidation]
      AC401(Some(999999)).validate(boxRetriever) shouldBe Set.empty[CtValidation]
    }

    "show correct error messages where AC401 is outside limit" in {
      when(boxRetriever.ac403()).thenReturn(AC403(Some(0)))

      AC401(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC401"),"error.AC401.mustBeZeroOrPositive",None))
      AC401(Some(-0)).validate(boxRetriever) shouldBe Set.empty[CtValidation]
      AC401(Some(1000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC401"),"error.AC401.exceeds.max",Some(List("999999"))))
    }

    "show correct error message when AC401 doesn't need to be present" in{
      when(boxRetriever.ac403()).thenReturn(AC403(Some(1)))

      AC401(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC401"),"error.AC401.required",None))
    }

    "don't show error message when AC401 should be present" in{
      when(boxRetriever.ac403()).thenReturn(AC403(None))

      AC401(None).validate(boxRetriever) shouldBe Set.empty[CtValidation]
    }
  }
}
