/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs10x.boxes.AC17
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.utils.CatoLimits._

class AC17Spec extends AccountsMoneyValidationFixture[Frs10xAccountsBoxRetriever] with AccountsPreviousPeriodValidationFixture[Frs10xAccountsBoxRetriever] with MockFrs102AccountsRetriever {

  testAccountsPreviousPoAValidation("AC17", AC17.apply)
 }
