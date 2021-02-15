/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP130Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter {

  val mockRetriever = mock[ComputationsBoxRetriever]

  before {
    reset(mockRetriever)
  }

  "CP130" should {
    "pass validation when CP130 equal to CP123+cp127" in {
      when(mockRetriever.cp123()).thenReturn(CP123(Some(3)))
      when(mockRetriever.cp127()).thenReturn(CP127(Some(2)))
      CP130.calculate(mockRetriever) shouldBe CP130(5)
    }
  }
}
