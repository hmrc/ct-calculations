/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{FunSuite, Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP999Spec extends WordSpec with Matchers with MockitoSugar  {
  "CP999" should {
    "return 0 if didCompanyMakeDonations didCompanyMakeGrassrootsDonations are both false" in {
      val boxRetriever = mock[ComputationsBoxRetriever]
      when(boxRetriever.cpQ21()).thenReturn(CPQ21(Some(false)))
      when(boxRetriever.cpQ321()).thenReturn(CPQ321(Some(false)))

      CP999.calculate(boxRetriever) shouldBe CP999(0)
    }
    "return sum values if didCompanyMakeDonations is true and didCompanyMakeGrassrootsDonations is false" in {
      val boxRetriever = mock[ComputationsBoxRetriever]
      when(boxRetriever.cpQ21()).thenReturn(CPQ21(Some(true)))
      when(boxRetriever.cpQ321()).thenReturn(CPQ321(Some(false)))
      when(boxRetriever.cp301()).thenReturn(CP301(1))
      when(boxRetriever.cp302()).thenReturn(CP302(2))

      CP999.calculate(boxRetriever) shouldBe CP999(3)
    }

    "return sum values if didCompanyMakeDonations is false and didCompanyMakeGrassrootsDonations is true" in {
      val boxRetriever = mock[ComputationsBoxRetriever]
      when(boxRetriever.cpQ21()).thenReturn(CPQ21(Some(false)))
      when(boxRetriever.cpQ321()).thenReturn(CPQ321(Some(true)))
      when(boxRetriever.cp3010()).thenReturn(CP3010(1))
      when(boxRetriever.cp3020()).thenReturn(CP3020(2))

      CP999.calculate(boxRetriever) shouldBe CP999(3)
    }

    "return sum values if didCompanyMakeDonations is true and didCompanyMakeGrassrootsDonations is true" in {
      val boxRetriever = mock[ComputationsBoxRetriever]
      when(boxRetriever.cpQ21()).thenReturn(CPQ21(Some(true)))
      when(boxRetriever.cpQ321()).thenReturn(CPQ321(Some(true)))
      when(boxRetriever.cp3010()).thenReturn(CP3010(1))
      when(boxRetriever.cp3020()).thenReturn(CP3020(2))
      when(boxRetriever.cp301()).thenReturn(CP301(3))
      when(boxRetriever.cp302()).thenReturn(CP302(4))

      CP999.calculate(boxRetriever) shouldBe CP999(10)
    }
  }
}
