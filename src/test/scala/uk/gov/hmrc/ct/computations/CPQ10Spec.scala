/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CPQ10Spec extends WordSpec with Matchers with MockitoSugar {



  val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
  val beforeSBA = LocalDate.parse("2018-10-29")
  when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))


  "CPQ10" should {
    "when empty" when {
      "pass validation when CPQ7 is false" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ10(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ10(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ7 is true" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ10(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ10"), "error.CPQ10.required"))
      }
    }
    "when false" when {
      "pass validation when CPQ7 is false" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ10(Some(false)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ10(Some(false)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is true" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ10(Some(false)).validate(boxRetriever) shouldBe empty
      }
    }
    "when true" when {
      "fail validation when CPQ7 is false" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ10(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ10"), "error.CPQ10.notClaiming.required"))
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ10(Some(true)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is true" in {
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ10(Some(true)).validate(boxRetriever) shouldBe empty
      }
    }
  }
}