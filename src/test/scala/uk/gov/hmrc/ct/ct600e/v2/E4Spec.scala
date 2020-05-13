

package uk.gov.hmrc.ct.ct600e.v2

import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

class E4Spec extends WordSpec with MockitoSugar with Matchers  {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E4 validation" should {
    "make E4 required when E2 < E1" in {
      when(boxRetriever.e1()).thenReturn(E1(Some(2)))
      when(boxRetriever.e2()).thenReturn(E2(Some(1)))
      E4(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("E4"), "error.E4.conditionalRequired", None))
    }
    "make E4 required when E2 empty but E1 is set" in {
      when(boxRetriever.e1()).thenReturn(E1(Some(1)))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E4(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("E4"), "error.E4.conditionalRequired", None))
    }

    "enforce E4 empty when E2 > E1" in {
      when(boxRetriever.e1()).thenReturn(E1(Some(1)))
      when(boxRetriever.e2()).thenReturn(E2(Some(2)))
      E4(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E4"), "error.E4.conditionalMustBeEmpty"))
    }
    "make E4 not required when E2 set but E1 empty" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(Some(2)))
      E4(None).validate(boxRetriever) shouldBe Set()
    }
    "make E4 not required when E2 and E1 are both empty" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E4(None).validate(boxRetriever) shouldBe Set()
    }

    "E4 invalid if number less that 1" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E4(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E4"), "error.E4.outOfRange", None))
    }
    "E4 valid if number 1 or greater" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E4(Some(1)).validate(boxRetriever) shouldBe Set()
    }
  }


}
