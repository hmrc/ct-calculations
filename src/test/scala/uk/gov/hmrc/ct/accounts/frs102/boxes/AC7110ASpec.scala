

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC7110ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac7110A()).thenReturn(AC7110A(Some("text")))
  }

  testTextFieldValidation("AC7110A", AC7110A, testUpperLimit = Some(StandardCohoTextFieldLimit))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC7110A", AC7110A)

}
