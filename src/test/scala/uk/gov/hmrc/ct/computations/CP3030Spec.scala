package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP3030Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever(true)

  testMandatoryWhen("CP3030", CP3030.apply, validValue = 1)

  testBoxIsZeroOrPositive("CP3030", CP3030.apply)

  testBecauseOfDependendBoxThenCannotExist("CP3030", CP3030.apply) {
    makeBoxRetriever(false)
  }

  private def makeBoxRetriever(cpq321Value: Boolean) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cpQ321()).thenReturn(CPQ321(Some(cpq321Value)))
    retriever
  }
}
