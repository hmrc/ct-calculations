package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP51Spec  extends WordSpec with MockitoSugar with Matchers {

  val boxRetriever = mock[ComputationsBoxRetriever]

  "CP51" should {

    " be valid if  cp 33 is NEGATIVE and someone enters in the same number " in {
      when(boxRetriever.cp33()).thenReturn(CP33(Some(-10)))
      CP51(Some(-10)).validate(boxRetriever) shouldBe Set()
    }
  }
}
