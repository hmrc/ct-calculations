/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

class B295Spec extends WordSpec with MockitoSugar with Matchers {

  "B295" should {
    "equal B275 + B285" in {
      val retriever = mock[CT600BoxRetriever]
      when(retriever.b275()).thenReturn(B275(1))
      when(retriever.b285()).thenReturn(B285(1))
      B295.calculate(retriever) shouldBe B295(2)
    }
  }
}
