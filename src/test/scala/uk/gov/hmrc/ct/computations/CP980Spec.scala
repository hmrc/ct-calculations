package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import org.mockito.Mockito.when
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP980Spec extends WordSpec with Matchers with MockitoSugar {

  "CP980 validation" should {

    "show correct error" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def ac401 = AC401(1000)

        override def ac403 = AC403(500)
      }


      val result = CP980(Some(1000)).validate(boxRetriever)

      result shouldBe Set(CtValidation(Some("CP980"), "error.cp980.breakdown"))
    }

    " not show error if value is entered correctly" in {
      val boxRetriever = new StubbedComputationsBoxRetriever {
        override def ac401 = AC401(1000)

        override def ac403 = AC403(250)
      }


      val result = CP980(Some(500)).validate(boxRetriever)

      result shouldBe Set.empty
    }


  }
}
