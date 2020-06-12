/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.prop.Tables.Table
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP287Spec extends WordSpec with Matchers with MockitoSugar {

  "CP287" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
    "when empty" when {
      "pass validation when CPQ20 is empty" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(None))
        CP287(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ20 is false" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(false)))
        CP287(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ20 is true" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CP287(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP287"), "error.CP287.required"))
      }
    }
    "when has value" when {
      "pass validation when CPQ20 is true and value < CP118 - CP998" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CP287(Some(80)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ20 is true and value == CP118 - CP998" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CP287(Some(90)).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ20 is true and value > CP118 - CP998" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CP287(Some(91)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP287"), "error.CP287.exceeds.max", Some(List("90"))))
      }
      "fail validation when CPQ20 is false" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(false)))
        CP287(Some(80)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP287"), "error.CP287.cannot.exist"))
      }
      "fail validation when CPQ20 is empty" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(None))
        CP287(Some(80)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP287"), "error.CP287.cannot.exist"))
      }
    }
  }
}
