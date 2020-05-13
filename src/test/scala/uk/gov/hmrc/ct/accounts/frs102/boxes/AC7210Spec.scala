

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC7210Spec extends WordSpec with MockitoSugar with Matchers with MockFrs102AccountsRetriever with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac32()).thenReturn(AC32(Some(4)))
  }

  testTextFieldValidation("AC7210", AC7210, testLowerLimit = Some(0), testUpperLimit = Some(StandardCohoTextFieldLimit))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC7210", AC7210)

  "AC7210" should {

    "fail validation when populated and AC32 is empty" in {
      when(boxRetriever.ac32()).thenReturn(AC32(None))
      AC7210(Some("testing")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210"), "error.AC7210.cannot.exist"))
    }
  }
}
