

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP301Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever(true)

  testMandatoryWhen("CP301", CP301.apply) {
    makeBoxRetriever(cpq21Value = true)
  }

  testBoxIsZeroOrPositive("CP301", CP301.apply)

  testCannotExistWhen("CP301", CP301.apply) {
    makeBoxRetriever(false)
  }

  private def makeBoxRetriever(cpq21Value: Boolean) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cpQ21()).thenReturn(CPQ21(Some(cpq21Value)))
    retriever
  }

}
