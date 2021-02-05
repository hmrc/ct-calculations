/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.AccountStatementValidationFixture
import uk.gov.hmrc.ct.accounts.frs102.helper.DirectorsReportEnabled
import uk.gov.hmrc.ct.accounts.frs10x.AC8899
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class AC8899Spec extends AccountStatementValidationFixture[Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever] {

  trait MockRetriever extends Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever
  override val boxRetriever: MockRetriever = mock[MockRetriever] (RETURNS_SMART_NULLS)

  override def setupMocks() = {
    when(boxRetriever.directorsReportEnabled()).thenReturn(DirectorsReportEnabled(true))
  }

  doStatementValidationTests("AC8899", AC8899.apply)

  "validation passes if not enabled" in {
    when(boxRetriever.directorsReportEnabled()).thenReturn(DirectorsReportEnabled(false))
    AC8899(None).validate(boxRetriever) shouldBe Set.empty
  }
}
