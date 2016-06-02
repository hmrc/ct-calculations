package uk.gov.hmrc.ct.computations

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CPQ19Spec extends WordSpec with Matchers with MockitoSugar {

  "CPQ19" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

    "when empty" when {
      "pass validation when CP118 is zero" in {
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(0))
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(10))
        CPQ19(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CATO01 is zero" in {
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(0))
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(10))
        CPQ19(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when both CATO01 and CP118 are greater than zero" in {
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(10))
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(10))
        CPQ19(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.required"))
      }
      "pass validation when both CATO01 and CP118 are zero" in {
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(0))
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(0))
        CPQ19(None).validate(boxRetriever) shouldBe empty
      }
    }
    "when true" when {
      "fail validation when CP118 is zero" in {
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(0))
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(10))
        CPQ19(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist.cp118"))
      }
      "fail validation when CATO01 is zero" in {
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(0))
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(10))
        CPQ19(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist.cato01"))
      }
      "fail validation when both CATO01 and CP118 are zero" in {
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(0))
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(0))
        CPQ19(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist"))
      }
      "pass validation when both CATO01 and CP118 are greater than zero" in {
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(10))
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(10))
        CPQ19(Some(true)).validate(boxRetriever) shouldBe empty
      }
    }
    "when false" when {
      "fail validation when CP118 is zero" in {
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(0))
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(10))
        CPQ19(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist.cp118"))
      }
      "fail validation when CATO01 is zero" in {
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(0))
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(10))
        CPQ19(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist.cato01"))
      }
      "fail validation when both CATO01 and CP118 are zero" in {
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(0))
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(0))
        CPQ19(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist"))
      }
      "pass validation when both CATO01 and CP118 are greater than zero" in {
        when(boxRetriever.retrieveCATO01()).thenReturn(CATO01(10))
        when(boxRetriever.retrieveCP118()).thenReturn(CP118(10))
        CPQ19(Some(false)).validate(boxRetriever) shouldBe empty
      }
    }
  }
}
