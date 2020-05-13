

package uk.gov.hmrc.ct.ct600e.v3

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

class E25Spec extends WordSpec with MockitoSugar with Matchers {


  "E25 calculated from E20" should {
    "be None is E20 is None" in {
      val boxRetriever = mock[CT600EBoxRetriever]
      when(boxRetriever.e20()).thenReturn(E20(None))
      E25.calculate(boxRetriever) shouldBe E25(None)
    }
    "be true is E20 is false" in {
      val boxRetriever = mock[CT600EBoxRetriever]
      when(boxRetriever.e20()).thenReturn(E20(Some(false)))
      E25.calculate(boxRetriever) shouldBe E25(Some(true))
    }
    "be false is E20 is true" in {
      val boxRetriever = mock[CT600EBoxRetriever]
      when(boxRetriever.e20()).thenReturn(E20(Some(true)))
      E25.calculate(boxRetriever) shouldBe E25(Some(false))
    }
  }

}
