/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


class CP672aSpec extends WordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] {

  val boxRetriever = mock[ComputationsBoxRetriever]

  override def setUpMocks = {
    when(boxRetriever.cp672()).thenReturn(CP672(Some(50)))
  }

  testBoxIsZeroOrPositive("CP672a", CP672a.apply)

  "when non empty" when {
    "fail validation when greater than CP672" in {
      CP672a(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP672a"), "error.CP672a.exceeds.max",Some(List("50"))))
    }
    "pass validation when lesser than CP672" in {
      CP672a(Some(40)).validate(boxRetriever) shouldBe Set()
    }
  }
}
