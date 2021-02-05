/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, MockFullAccountsRetriever}
import uk.gov.hmrc.ct.accounts.frsAny.boxes.AC14
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever

class AC14Spec extends AccountsMoneyValidationFixture[AccountsBoxRetriever] with MockFullAccountsRetriever {

  testAccountsMoneyValidationWithMin(boxId = "AC14", minValue = 0, AC14.apply)
}
