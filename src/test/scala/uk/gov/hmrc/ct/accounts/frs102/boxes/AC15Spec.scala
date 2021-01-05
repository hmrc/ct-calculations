/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, AccountsPreviousPeriodValidationFixture, MockFullAccountsRetriever}

class AC15Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with AccountsPreviousPeriodValidationFixture[Frs102AccountsBoxRetriever] with MockFullAccountsRetriever {

  testAccountsMoneyValidationWithMin("AC15", 0, AC15.apply)

  testAccountsPreviousPoAValidation("AC15", AC15.apply)
}
