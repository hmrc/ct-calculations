package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSuite, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.version.calculations.ComputationsBoxRetrieverForTest


class CP78Spec extends WordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] {

  val boxRetriever = mock[ComputationsBoxRetrieverForTest]

  override def setUpMocks = {
    when(boxRetriever.cpQ8()).thenReturn(CPQ8(Some(false)))
  }


  testBoxIsZeroOrPositive("CP78", CP78.apply _)

  "CP78" should {
    "return cannot exist error when CPQ8 is true" in {
      val value = 2
      val mockBoxRetriever = mock[ComputationsBoxRetrieverForTest]

      when(mockBoxRetriever.cpQ8()).thenReturn(CPQ8(Some(true)))

      CP78(Some(value)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("CP78"), s"error.CP78.cannot.exist"))
    }
  }
}
