/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

import scala.util.Random

class CPQ17Spec extends WordSpec with Matchers with MockitoSugar {
  import CPQ17Spec._

  "CPQ17" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

    "when empty" when {
      "pass validation when CP117 == zero" in {
        when(boxRetriever.cp2()).thenReturn(doesntMatter())
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp117()).thenReturn(CP117(0))
        CPQ17(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CP117 == zero but with relevant NTP" in {
        when(boxRetriever.cp2()).thenReturn(afterLossReform)
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp117()).thenReturn(CP117(0))
        CPQ17(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ17"), "error.CPQ17.required"))
      }
      "fail validation when CP117 > 0" in {
        when(boxRetriever.cp2()).thenReturn(beforeLossReform)
        when(boxRetriever.cp117()).thenReturn(CP117(10))
        CPQ17(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ17"), "error.CPQ17.required"))
      }
    }
    "when true" when {
      "fail validation when CP117 is zero" in {
        when(boxRetriever.cp2()).thenReturn(doesntMatter())
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp117()).thenReturn(CP117(0))
        CPQ17(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ17"), "error.CPQ17.cannot.exist"))
      }
      "pass validation when CP117 is zero but with relevant NTP" in {
        when(boxRetriever.cp2()).thenReturn(afterLossReform)
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp117()).thenReturn(CP117(0))
        CPQ17(Some(true)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CP117 > zero" in {
        when(boxRetriever.cp2()).thenReturn(beforeLossReform)
        when(boxRetriever.cp117()).thenReturn(CP117(10))
        CPQ17(Some(true)).validate(boxRetriever) shouldBe empty
      }
    }
  }
}

object CPQ17Spec {
  def doesntMatter(): CP2 = if (Random.nextBoolean()) beforeLossReform else afterLossReform
  val beforeLossReform = CP2(LocalDate.parse("2016-03-01"))
  val afterLossReform = CP2(LocalDate.parse("2017-04-01"))
}