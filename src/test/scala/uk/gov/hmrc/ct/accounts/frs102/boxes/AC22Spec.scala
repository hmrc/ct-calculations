/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever

class AC22Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  testAccountsMoneyValidation("AC22", AC22.apply)
}
