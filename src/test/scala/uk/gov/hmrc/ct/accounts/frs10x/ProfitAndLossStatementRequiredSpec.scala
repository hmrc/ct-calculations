/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs10x

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.HMRCFiling
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xDirectorsBoxRetriever, Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class ProfitAndLossStatementRequiredSpec extends WordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {

  trait MockRetriever extends Frs10xDormancyBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever
  val mockBoxRetriever: MockRetriever = mock[MockRetriever] (RETURNS_SMART_NULLS)

  "NotTradedStatementRequired should" should {

    "be false if not dormant" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(None))
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      ProfitAndLossStatementRequired.calculate(mockBoxRetriever) shouldBe ProfitAndLossStatementRequired(false)

      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(false)))
      ProfitAndLossStatementRequired.calculate(mockBoxRetriever) shouldBe ProfitAndLossStatementRequired(false)
    }

    "be false if dormant, coho only and not filing P&L to coho" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))

      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))
      ProfitAndLossStatementRequired.calculate(mockBoxRetriever) shouldBe ProfitAndLossStatementRequired(false)
    }

    "be true if dormant and 1) not coho only or 2) filing P&L to coho" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))

      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))
      ProfitAndLossStatementRequired.calculate(mockBoxRetriever) shouldBe ProfitAndLossStatementRequired(true)

      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))
      ProfitAndLossStatementRequired.calculate(mockBoxRetriever) shouldBe ProfitAndLossStatementRequired(true)
    }
  }
}
