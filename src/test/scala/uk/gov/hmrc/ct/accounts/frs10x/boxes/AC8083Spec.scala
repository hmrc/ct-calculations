/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.mockito.Mockito._
import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.ct.accounts.AccountStatementValidationFixture
import uk.gov.hmrc.ct.accounts.frs10x.boxes.AC8083
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever

class AC8083Spec extends AccountStatementValidationFixture[Frs10xAccountsBoxRetriever] with BeforeAndAfterEach {

  override val boxRetriever = mock[MockRetriever] (RETURNS_SMART_NULLS)

  doStatementValidationTests("AC8083", AC8083.apply)
}
