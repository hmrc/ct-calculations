

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


class CP667Spec extends WordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] {

  val boxRetriever = mock[ComputationsBoxRetriever]

  override def setUpMocks = {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
  }


  testBoxIsZeroOrPositive("CP667", CP667.apply)

  testCannotExistWhen("CP667", CP667.apply) {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(true))).getMock[ComputationsBoxRetriever]
  }
}
