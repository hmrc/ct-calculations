/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import org.joda.time.LocalDate
import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsMoneyValidationFixture, AC206}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever

class AC302ASpec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()
    when(boxRetriever.ac206()).thenReturn(AC206(Some(new LocalDate())))
  }

  testAccountsMoneyValidationWithMin("AC302A", 0, AC302A.apply)

  "AC301A" should {
    "always pass if no previous POA" in {
      when(boxRetriever.ac206()).thenReturn(AC206(None))
      AC302A(Some(-99)).validate(boxRetriever) shouldBe Set()
    }
  }
}
