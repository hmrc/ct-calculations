/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC8021, AC8023, ACQ8009}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling, MicroEntityFiling}

class ACQ8009Spec extends WordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {

  val mockBoxRetriever = mock[MockableFrs10xBoxretrieverWithFilingAttributes]

  override def beforeEach = {
    DirectorsMockSetup.setupDefaults(mockBoxRetriever)
  }


  "ACQ8009 should" should {

    "validate successfully when no errors present" in {

      val secretary = ACQ8009(Some(true))

      secretary.validate(mockBoxRetriever) shouldBe empty
    }

    "validate as mandatory" in {

      val secretary = ACQ8009(None)

      secretary.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("ACQ8009"), "error.ACQ8009.required", None))
    }

    "no validate if no directors report" in {

      when(mockBoxRetriever.ac8021()).thenReturn(AC8021(None))
      when(mockBoxRetriever.ac8023()).thenReturn(AC8023(Some(false)))

      val secretary = ACQ8009(None)

      secretary.validate(mockBoxRetriever) shouldBe empty
    }
  }
}
