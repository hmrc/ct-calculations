/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.AC403
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP981Spec extends WordSpec with Matchers with MockitoSugar {
  "CP981 validation" should {
    "show correct error if under zero" in {
      val boxRetriever = new StubbedComputationsBoxRetriever

      val result = CP981(Some(-1)).validate(boxRetriever)

      result shouldBe Set(CtValidation(Some("CP981"), "error.CP981.mustBeZeroOrPositive"))
    }

    "doesn't show error if zero" in {
      val boxRetriever = new StubbedComputationsBoxRetriever

      val result = CP981(Some(0)).validate(boxRetriever)

      result shouldBe Set.empty
    }
  }

  "CP981 can be created from AC403" in {
    val testValue = Some(101)
    val ac403 = AC403(testValue)

    val cp981 = CP981(ac403)
    cp981.value shouldBe testValue
  }
}
