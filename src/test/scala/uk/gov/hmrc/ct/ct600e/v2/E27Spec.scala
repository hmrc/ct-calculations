

package uk.gov.hmrc.ct.ct600e.v2

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

class E27Spec extends WordSpec with MockitoSugar with Matchers  {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E4 validation" should {
    "make E27 required when E26 = 2" in {
      when(boxRetriever.e26()).thenReturn(E26(Some(CharityLoansAndInvestments.SomeLoansAndInvestments)))
      E27(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("E27"), "error.E27.required", None))
    }

    "don't validate E27 if E26 != 2" in {
      when(boxRetriever.e26()).thenReturn(E26(Some(CharityLoansAndInvestments.AllLoansAndInvestments)))
      E27(None).validate(boxRetriever) shouldBe Set()
    }

    "return validation error if value is 0" in {
      when(boxRetriever.e26()).thenReturn(E26(Some(CharityLoansAndInvestments.SomeLoansAndInvestments)))
      E27(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E27"), "error.E27.required", None))
    }

    "return validation error if value is -1" in {
      when(boxRetriever.e26()).thenReturn(E26(Some(CharityLoansAndInvestments.SomeLoansAndInvestments)))
      E27(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E27"), "error.E27.mustBePositive", None))
    }

    "don't return validation error if value is over 0" in {
      when(boxRetriever.e26()).thenReturn(E26(Some(CharityLoansAndInvestments.SomeLoansAndInvestments)))
      E27(Some(1)).validate(boxRetriever) shouldBe Set()
    }
  }
}
