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

class NotTradedStatementRequiredSpec extends WordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {

  trait MockRetriever extends Frs10xDormancyBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever
  val mockBoxRetriever: MockRetriever = mock[MockRetriever] (RETURNS_SMART_NULLS)

  "NotTradedStatementRequired should" should {

    "be false if not dormant" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(None))
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      NotTradedStatementRequired.calculate(mockBoxRetriever) shouldBe NotTradedStatementRequired(false)

      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(false)))
      NotTradedStatementRequired.calculate(mockBoxRetriever) shouldBe NotTradedStatementRequired(false)
    }

    "be false if dormant, coho only and not filing P&L to coho, and not filing directors report to coho" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))

      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))
      when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
      NotTradedStatementRequired.calculate(mockBoxRetriever) shouldBe NotTradedStatementRequired(false)
    }

    "be true if dormant and 1) not coho only or 2) filing P&L to coho or 3) filing directors report to coho" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))

      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))
      when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
      NotTradedStatementRequired.calculate(mockBoxRetriever) shouldBe NotTradedStatementRequired(true)

      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.acq8161()).thenReturn(ACQ8161(Some(true)))
      when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(false)))
      NotTradedStatementRequired.calculate(mockBoxRetriever) shouldBe NotTradedStatementRequired(true)

      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.acq8161()).thenReturn(ACQ8161(Some(false)))
      when(mockBoxRetriever.ac8021()).thenReturn(AC8021(Some(true)))
      NotTradedStatementRequired.calculate(mockBoxRetriever) shouldBe NotTradedStatementRequired(true)
    }
  }
}
