/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.mockito.Mockito._
import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.ct.accounts.AccountStatementValidationFixture
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever

class ACQ8989Spec extends AccountStatementValidationFixture[Frs10xDormancyBoxRetriever] with BeforeAndAfterEach {

  override val boxRetriever = mock[Frs10xDormancyBoxRetriever] (RETURNS_SMART_NULLS)

  override def setupMocks() = {
    when(boxRetriever.notTradedStatementRequired()).thenReturn(NotTradedStatementRequired(true))
  }

  doStatementValidationTests("ACQ8989", ACQ8989.apply)

  "ACQ8989 should" should {

    "validate successfully when not set and notTradedStatementRequired false" in {
      when(boxRetriever.notTradedStatementRequired()).thenReturn(NotTradedStatementRequired(false))
      ACQ8989(None).validate(boxRetriever) shouldBe Set.empty
    }
  }
}
