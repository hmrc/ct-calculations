/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, AccountsPreviousPeriodValidationFixture, MockFullAccountsRetriever}

class AC15Spec extends AccountsMoneyValidationFixture[Frs10xAccountsBoxRetriever] with AccountsPreviousPeriodValidationFixture[Frs10xAccountsBoxRetriever] with MockFullAccountsRetriever {

  testAccountsMoneyValidationWithMin("AC15", 0, AC15.apply)

  testAccountsPreviousPoAValidation("AC15", AC15.apply)
}
