/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.mockito.Mockito._
import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.ct.accounts.AccountStatementValidationFixture
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever

class ACQ8990Spec extends AccountStatementValidationFixture[Frs10xDormancyBoxRetriever] with BeforeAndAfterEach {

  override val boxRetriever = mock[Frs10xDormancyBoxRetriever] (RETURNS_SMART_NULLS)

  override def setupMocks() = {
    when(boxRetriever.profitAndLossStatementRequired()).thenReturn(ProfitAndLossStatementRequired(true))
  }

  doStatementValidationTests("ACQ8990", ACQ8990.apply)

  "ACQ8990 should" should {

    "validate successfully when not set and notTradedStatementRequired false" in {
      when(boxRetriever.profitAndLossStatementRequired()).thenReturn(ProfitAndLossStatementRequired(false))
      ACQ8990(None).validate(boxRetriever) shouldBe Set.empty
    }
  }
}
