

package uk.gov.hmrc.ct.computations

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class AP3Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = mock[ComputationsBoxRetriever]

  testBoxIsZeroOrPositive("AP3", v => AP3(v))
}
