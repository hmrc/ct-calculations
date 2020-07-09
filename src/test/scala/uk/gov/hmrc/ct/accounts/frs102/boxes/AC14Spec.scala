/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, MockFullAccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}

class AC14Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFullAccountsRetriever {

  testAccountsMoneyValidationWithMin(boxId = "AC14", minValue = 0, AC14.apply)
}
