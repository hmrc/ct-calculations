/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.mockito.Mockito._
import org.scalatest.BeforeAndAfterEach
import uk.gov.hmrc.ct.accounts.AccountStatementValidationFixture
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xAccountsBoxRetriever, Frs10xDirectorsBoxRetriever, Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


trait MockRetriever extends Frs10xAccountsBoxRetriever with Frs10xDormancyBoxRetriever {
  self: FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever =>
}

class AC8081Spec extends AccountStatementValidationFixture[Frs10xAccountsBoxRetriever with Frs10xDormancyBoxRetriever] with BeforeAndAfterEach {

  override val boxRetriever = mock[MockRetriever] (RETURNS_SMART_NULLS)

  override def setupMocks = {
    when(boxRetriever.acq8999()).thenReturn(ACQ8999(None))
  }

  doStatementValidationTests("AC8081", AC8081.apply)


  "validation disabled if dormant" in {
    when(boxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))

    AC8081(None).validate(boxRetriever) shouldBe Set.empty
    AC8081(Some(true)).validate(boxRetriever) shouldBe Set.empty
  }
}
