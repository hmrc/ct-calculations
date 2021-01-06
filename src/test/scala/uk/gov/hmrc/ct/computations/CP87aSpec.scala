/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


class CP87aSpec extends WordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] with BeforeAndAfterEach {

  val boxRetriever = mock[ComputationsBoxRetriever]

  override def setUpMocks = {
    when(boxRetriever.cp87Input()).thenReturn(CP87Input(Some(50)))
  }

  testBoxIsZeroOrPositive("CP87a", CP87a.apply)

  "when non empty" when {
    "fail validation when greater than CP87" in {
      CP87a(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP87a"), "error.CP87a.exceeds.max",Some(List("50"))))
    }
    "pass validation when lesser than CP87" in {
      CP87a(Some(40)).validate(boxRetriever) shouldBe Set()
    }
  }
}