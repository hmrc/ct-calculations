/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.{MockFullAccountsRetriever, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever

class AC14Spec extends AccountsMoneyValidationFixture[FullAccountsBoxRetriever] with MockFullAccountsRetriever {

  testAccountsMoneyValidationWithMin(boxId = "AC14", minValue = 0, AC14.apply)
}
