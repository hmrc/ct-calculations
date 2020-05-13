

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CPQ1000Spec extends WordSpec with Matchers with MockitoSugar {

  "CPQ1000" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

    "when empty" when {
      "pass validation when CPQ7 is false" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ1000(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ1000(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ7 is true" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ1000(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ1000"), "error.CPQ1000.required"))
      }
    }
    "when false" when {
      "fail validation when CPQ7 is false" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ1000(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ1000"), "error.CPQ1000.cannot.exist.without.cpq7"))
      }

      "fail validation when CPQ7 is empty" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ1000(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ1000"), "error.CPQ1000.cannot.exist.without.cpq7"))
      }

      "pass validation when CPQ7 is true" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ1000(Some(false)).validate(boxRetriever) shouldBe empty
      }
    }
    "when true" when {
      "fail validation when CPQ7 is false" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ1000(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ1000"), "error.CPQ1000.cannot.exist.without.cpq7"))
      }

      "fail validation when CPQ7 is empty" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ1000(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ1000"), "error.CPQ1000.cannot.exist.without.cpq7"))
      }

      "pass validation when CPQ7 is true" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ1000(Some(true)).validate(boxRetriever) shouldBe empty
      }
    }
  }
}
