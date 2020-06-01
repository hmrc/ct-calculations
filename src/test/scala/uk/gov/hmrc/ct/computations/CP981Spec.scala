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
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(Some(1))
      }

      val result = CP981(Some(-1)).validate(boxRetriever)

      result shouldBe Set(CtValidation(Some("CP981"), "error.CP981.mustBeZeroOrPositive"))
    }

    "CP981 can't be greater than CP983" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(Some(10))
      }

      val result = CP981(Some(11)).validate(boxRetriever)

      result shouldBe Set(CtValidation(Some("CP981"), "error.CP981.exceeds.CP983"))
    }

    "doesn't show error if zero" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(Some(1))
      }

      val result = CP981(Some(0)).validate(boxRetriever)

      result shouldBe Set.empty
    }

    "doesn't show error if None" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(Some(1))
      }

      val result = CP981(None).validate(boxRetriever)

      result shouldBe Set.empty
    }
  }
}
