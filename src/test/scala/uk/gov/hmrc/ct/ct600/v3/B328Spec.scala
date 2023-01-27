package uk.gov.hmrc.ct.ct600.v3

import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class B328Spec extends WordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] {

  val boxRetriever =  mock[ComputationsBoxRetriever]

  testBoxIsZeroOrPositive("B328", B328.apply)
}
