/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.utils.CatoLimits._

class AC16Spec extends AccountsMoneyValidationFixture[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever] with MockFrs102AccountsRetriever {
  testAccountsMoneyValidationWithMinMax("AC16", minimumValue, turnoverHMRCMaximumValue, AC16.apply)
  }
