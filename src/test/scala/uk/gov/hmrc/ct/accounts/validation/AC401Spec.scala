/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.validation

import org.mockito.Mockito.when
import uk.gov.hmrc.ct.CATO24
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.utils.UnitSpec

class AC401Spec extends UnitSpec {

  "AC401 validation" should {
    val boxRetriever = mock[AccountsBoxRetriever]
    val opwIsEnabled: CATO24 = CATO24(Some(true))
    val opwNotEnabled: CATO24 = CATO24(Some(false))
    val emptyOPWDeductions = AC403(Some(0))

    when(boxRetriever.cato24()).thenReturn(opwIsEnabled)

    "not show error messages where AC401 is within limit" in {
      when(boxRetriever.ac403()).thenReturn(emptyOPWDeductions)

      AC401(Some(0)).validate(boxRetriever) shouldBe validationSuccess
      AC401(Some(999999)).validate(boxRetriever) shouldBe validationSuccess
    }

    "show correct error messages where AC401 is outside limit" in {
      when(boxRetriever.ac403()).thenReturn(emptyOPWDeductions)

      AC401(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC401"),"error.AC401.mustBeZeroOrPositive",None))
      AC401(Some(-0)).validate(boxRetriever) shouldBe validationSuccess
      AC401(Some(1000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC401"),"error.AC401.exceeds.max",Some(List("999999"))))
    }

    "show correct error message when AC401 doesn't need to be present" in{
      when(boxRetriever.ac403()).thenReturn(AC403(Some(1)))

      AC401(None).validate(boxRetriever) shouldBe fieldRequiredError("AC401")
    }

    "don't show error message when AC401 shouldn't be present" in{
      when(boxRetriever.cato24()).thenReturn(opwNotEnabled)


      AC401(None).validate(boxRetriever) shouldBe validationSuccess
    }
  }
}
