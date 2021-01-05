/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs102.stubs.StubbedFullAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs105.boxes.AC415

class AC17Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with AccountsPreviousPeriodValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  testAccountsMoneyValidation("AC17", AC17.apply)

  testAccountsPreviousPoAValidation("AC17", AC17.apply)
 }
