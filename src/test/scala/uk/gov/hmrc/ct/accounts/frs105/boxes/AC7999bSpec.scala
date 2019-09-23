package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs105AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7999bSpec extends WordSpec with Matchers with MockitoSugar with MockFrs105AccountsRetriever {

  private def fieldRequiredError(boxID: String) = CtValidation(Some(boxID), s"error.$boxID.required")

  private val boxID = "AC7999b"

  when(boxRetriever.ac7999a()).thenReturn(AC7999a(None))

  "AC7999b" should {
    "fail validation if this radio button and AC7999a are unchecked" in {
      AC7999b(Some(false)).validate(boxRetriever) shouldBe Set(fieldRequiredError(boxID))
      AC7999b(None).validate(boxRetriever) shouldBe Set(fieldRequiredError(boxID))
    }

    "pass validation if this radio button is checked and AC7999b is unchecked" in {
      AC7999b(Some(true)).validate(boxRetriever) shouldBe Set.empty
    }
  }
}
