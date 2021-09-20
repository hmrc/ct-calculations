/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP980Spec extends WordSpec with Matchers with MockitoSugar {

  "CP980 validation" should {

    "show correct error" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(1000)

        override def cp981 = CP981(500)
      }


      val result = CP980(Some(1000)).validate(boxRetriever)

      result shouldBe Set(CtValidation(Some("CP980"), "error.cp980.breakdown", Some(List("500"))))
    }

    " not show error if value is entered correctly" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def cp983 = CP983(1000)

        override def cp981 = CP981(250)
      }


      val result = CP980(Some(500)).validate(boxRetriever)

      result shouldBe Set.empty
    }
  }
}
