/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.mockito.Mockito._
import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.ct.accounts.AccountStatementValidationFixture
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC8089, ACQ8999}

class AC8089Spec extends AccountStatementValidationFixture[Frs10xDormancyBoxRetriever] with BeforeAndAfterEach {

  override val boxRetriever = mock[Frs10xDormancyBoxRetriever] (RETURNS_SMART_NULLS)

  override def setupMocks() = {
    when(boxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))
  }

  doStatementValidationTests("AC8089", AC8089.apply)

  "validation passes if not dormant" in {
      when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
      AC8089(None).validate(boxRetriever) shouldBe Set.empty

      when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
      AC8089(Some(true)).validate(boxRetriever) shouldBe Set.empty

      when(boxRetriever.acq8999()).thenReturn(ACQ8999(Some(false)))
      AC8089(Some(true)).validate(boxRetriever) shouldBe Set.empty
  }
}
