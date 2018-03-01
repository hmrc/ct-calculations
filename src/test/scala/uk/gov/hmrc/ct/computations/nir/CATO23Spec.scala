package uk.gov.hmrc.ct.computations.nir

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.computations.CP997NI
import uk.gov.hmrc.ct.{BoxValidationFixture, CATO01, CATO23}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CATO23Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever()

  private def makeBoxRetriever(cato01Value: Int = 500, cp997NIValue: Option[Int] = Some(200)) = {

    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cato01()).thenReturn(CATO01(cato01Value))
    when(retriever.cp997NI()).thenReturn(CP997NI(cp997NIValue))
    retriever
  }

  "CATO23" should{
    "be calculated" in{
      CATO23.calculate(makeBoxRetriever()) shouldBe  CATO23(300)
    }
  }

}
