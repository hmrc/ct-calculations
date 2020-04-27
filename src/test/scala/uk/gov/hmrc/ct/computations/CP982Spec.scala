/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP982Spec extends WordSpec with Matchers with MockitoSugar {

  "CP982 validation" should {

    "show correct error" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def ac401 = AC401(1000)

        override def ac403 = AC403(500)

        override def cp980 = CP980(500)
      }

      val result = CP982(Some(500)).validate(boxRetriever)

      result shouldBe Set(CtValidation(Some("CP982"), "error.cp982.breakdown"))
    }

    " not show error if value is entered correctly" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def ac401 = AC401(1000)

        override def ac403 = AC403(250)

        override def cp980 = CP980(250)
      }


      val result = CP982(Some(500)).validate(boxRetriever)

      result shouldBe Set.empty
    }
  }
}
