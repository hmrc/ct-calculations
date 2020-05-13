

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC301ASpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter
  with MockFrs102AccountsRetriever with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  testTextFieldValidation("AC301A", AC301A, testUpperLimit = Some(StandardCohoTextFieldLimit), testMandatory = Some(true))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC301A", AC301A)

  "AC301A" should {
    "be mandatory" in {
      AC301A(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC301A"), "error.AC301A.required", None))
    }
  }
}
