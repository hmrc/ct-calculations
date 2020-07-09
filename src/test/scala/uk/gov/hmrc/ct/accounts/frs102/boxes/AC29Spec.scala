/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsPreviousPeriodValidationFixture, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever

class AC29Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with AccountsPreviousPeriodValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  testAccountsMoneyValidation("AC29", AC29.apply)

  testAccountsPreviousPoAValidation("AC29", AC29.apply)
}
