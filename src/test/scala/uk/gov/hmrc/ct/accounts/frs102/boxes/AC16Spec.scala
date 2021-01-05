/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class AC16Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with MockFrs102AccountsRetriever {
  testAccountsMoneyValidation("AC16", AC16.apply)
  }
