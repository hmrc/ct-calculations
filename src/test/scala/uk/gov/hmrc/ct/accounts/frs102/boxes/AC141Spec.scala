

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, MockFullAccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC141Spec extends AccountsMoneyValidationFixture[FullAccountsBoxRetriever] with MockFullAccountsRetriever {

  "AC141" should {
    "fail validation if not equal to AC53" in {
      when(boxRetriever.ac52()).thenReturn(AC52(Some(5)))
      when(boxRetriever.ac53()).thenReturn(AC53(Some(13)))
      AC141(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.debtors.previousYearTotal.notEqualsTo.previousYearAmount"))
    }

    "fail validation if empty and AC53 is 0" in {
      when(boxRetriever.ac52()).thenReturn(AC52(Some(5)))
      when(boxRetriever.ac53()).thenReturn(AC53(Some(0)))
      AC141(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.debtors.previousYearTotal.notEqualsTo.previousYearAmount"))
    }

    "pass validation if equal to AC53" in {
      when(boxRetriever.ac52()).thenReturn(AC52(Some(5)))
      when(boxRetriever.ac53()).thenReturn(AC53(Some(13)))
      AC141(Some(13)).validate(boxRetriever) shouldBe Set.empty
    }
  }

}
