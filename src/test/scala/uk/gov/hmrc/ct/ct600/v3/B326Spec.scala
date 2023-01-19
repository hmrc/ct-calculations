package uk.gov.hmrc.ct.ct600.v3

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.computations.{CP1, CP2}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.version.calculations.ComputationsBoxRetrieverForTest

class B326Spec extends WordSpec with MockitoSugar with Matchers {

  "B326 validate" should {

    "not return errors when B326 is empty" in {
      val mockBoxRetriever = mock[ComputationsBoxRetrieverForTest]
      B326(Some(1)).validate(mockBoxRetriever) shouldBe B326(Some(1))
    }
  }
}
