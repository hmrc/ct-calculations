/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class LEC01Spec extends WordSpec with MockitoSugar with Matchers {

  "LEC01" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

    "when empty" when {
      "pass validation when CPQ1000 is false" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(false)))
        LEC01().validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ1000 is empty" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(None))
        LEC01().validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ1000 is true" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(true)))
        LEC01().validate(boxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.required"))
      }
    }
    "when cars exist" when {
      val cars = List(Car("LG64 RDO", true, 26000, 12, new LocalDate("2015-04-01")))
      "fail validation when CPQ1000 is false" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(false)))
        LEC01(cars).validate(boxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.cannot.exist"))
      }

      "fail validation when CPQ1000 is empty" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(None))
        LEC01(cars).validate(boxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.cannot.exist"))
      }

      "pass validation when CPQ1000 is true" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(true)))
        LEC01(cars).validate(boxRetriever) shouldBe empty
      }
    }
  }
}
