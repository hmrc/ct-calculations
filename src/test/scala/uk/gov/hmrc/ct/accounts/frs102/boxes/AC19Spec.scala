/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsPreviousPeriodValidationFixture, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever

class AC19Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with AccountsPreviousPeriodValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  testAccountsMoneyValidation("AC19", AC19.apply)

  testAccountsPreviousPoAValidation("AC19", AC19.apply)
}
