/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, MockFullAccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC140Spec extends AccountsMoneyValidationFixture[FullAccountsBoxRetriever] with MockFullAccountsRetriever {

  "AC140" should {
    "fail validation if not equal to AC52" in {
      when(boxRetriever.ac52()).thenReturn(AC52(Some(5)))
      when(boxRetriever.ac53()).thenReturn(AC53(Some(5)))
      AC140(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.debtors.currentYearTotal.notEqualsTo.currentYearAmount"))
    }

    "fail validation if not equal to empty AC52" in {
      when(boxRetriever.ac52()).thenReturn(AC52(None))
      when(boxRetriever.ac53()).thenReturn(AC53(Some(5)))
      AC140(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.debtors.currentYearTotal.notEqualsTo.currentYearAmount"))
    }

    "fail validation if empty and AC52 is 0" in {
      when(boxRetriever.ac52()).thenReturn(AC52(Some(0)))
      when(boxRetriever.ac53()).thenReturn(AC53(Some(5)))
      AC140(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.debtors.currentYearTotal.notEqualsTo.currentYearAmount"))
    }

    "pass validation if equal to AC52" in {
      when(boxRetriever.ac52()).thenReturn(AC52(Some(5)))
      when(boxRetriever.ac53()).thenReturn(AC53(Some(5)))
      AC140(Some(5)).validate(boxRetriever) shouldBe Set.empty
    }
  }

}
