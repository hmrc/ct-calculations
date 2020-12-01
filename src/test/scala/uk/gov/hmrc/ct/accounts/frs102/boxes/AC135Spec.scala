/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever

class AC135Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  testAccountsMoneyValidationWithMin("AC135",0, AC135.apply)

}
