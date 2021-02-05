/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever

class AC17Spec extends AccountsMoneyValidationFixture[Frs10xAccountsBoxRetriever] with AccountsPreviousPeriodValidationFixture[Frs10xAccountsBoxRetriever] with MockFrs102AccountsRetriever {

  testAccountsMoneyValidation("AC17", AC17.apply)

  testAccountsPreviousPoAValidation("AC17", AC17.apply)
 }
