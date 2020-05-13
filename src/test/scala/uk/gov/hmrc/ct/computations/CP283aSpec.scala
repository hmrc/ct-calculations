

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.Validators.LossesPreviousToCurrentFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP283aSpec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] with LossesPreviousToCurrentFixture {

  override val boxRetriever = makeBoxRetriever()

  testMandatoryWhen("CP283a", CP283a.apply) {
    makeBoxRetriever()
  }

  testBoxIsZeroOrPositive("CP283a", CP283a.apply)

  testGlobalErrorsForBroughtForwardGtTotalProfit(b => b.cp283a()) {
    makeBoxRetriever()
  }

  private def makeBoxRetriever(cp281aValue: Option[Int] = Some(1)) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp281a()).thenReturn(CP281a(cp281aValue))
    when(retriever.cp283a()).thenReturn(CP283a(None))
    when(retriever.cp283b()).thenReturn(CP283b(None))
    when(retriever.cp117()).thenReturn(CP117(0))
    retriever
  }
}
