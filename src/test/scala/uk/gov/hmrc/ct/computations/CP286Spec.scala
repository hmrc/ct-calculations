package uk.gov.hmrc.ct.computations

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.calculations.TradingLossesCP286MaximumCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP286Spec extends WordSpec with Matchers with MockitoSugar {

  "CP286" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
    "when empty" when {
      "pass validation when CPQ18 is empty" in {
        when(boxRetriever.retrieveCPQ18()).thenReturn(CPQ18(None))
        CP286(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ18 is false" in {
        when(boxRetriever.retrieveCPQ18()).thenReturn(CPQ18(Some(false)))
        CP286(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ18 is true" in {
        when(boxRetriever.retrieveCPQ18()).thenReturn(CPQ18(Some(true)))
        CP286(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286"), "error.CP286.required"))
      }
    }

    "when has value" when {
      "pass validation when CPQ18 is true and value < limit" in {
        when(boxRetriever.retrieveCPQ18()).thenReturn(CPQ18(Some(true)))
        val box = new CP286(Some(90)) {
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp998: CP998, cp281: CP281) = 91
        }
        box.validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ18 is true and value == limit" in {
        when(boxRetriever.retrieveCPQ18()).thenReturn(CPQ18(Some(true)))
        val box = new CP286(Some(90)) {
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp998: CP998, cp281: CP281) = 90
        }
        box.validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ18 is true and value > limit" in {
        when(boxRetriever.retrieveCPQ18()).thenReturn(CPQ18(Some(true)))
        val box = new CP286(Some(90)) {
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp998: CP998, cp281: CP281) = 89
        }
        box.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286"), "error.CP286.exceeds.max"))
      }
      "fail validation when CPQ18 is false and has value" in {
        when(boxRetriever.retrieveCPQ18()).thenReturn(CPQ18(Some(false)))
        val box = new CP286(Some(90)) {
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp998: CP998, cp281: CP281) = 90
        }
        box.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286"), "error.CP286.cannot.exist"))
      }
    }
  }
}