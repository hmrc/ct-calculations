/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP265Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter {

  val mockRetriever = mock[ComputationsBoxRetriever]

  before {
    reset(mockRetriever)
  }

  "CP265" should {
    "be CP293 with post-reform losses added back in" in {
      when(mockRetriever.cp293()).thenReturn(CP293(3))
      when(mockRetriever.cp283b()).thenReturn(CP283b(2))
      CP265.calculate(mockRetriever) shouldBe CP265(5)
    }
  }
}
