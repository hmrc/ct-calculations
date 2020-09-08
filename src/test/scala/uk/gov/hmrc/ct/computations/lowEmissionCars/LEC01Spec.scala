/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.lowEmissionCars

import org.joda.time.LocalDate
import org.mockito.Mockito._
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.{CPQ1000, Car, LEC01}
import uk.gov.hmrc.ct.utils.UnitSpec

class LEC01Spec extends UnitSpec {

  "LEC01" should {

    "when empty" when {
      "pass validation when CPQ1000 is false" in {
        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(false)))
        LEC01().validate(mockComputationsBoxRetriever) shouldBe empty
      }
      "pass validation when CPQ1000 is empty" in {
        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(None))
        LEC01().validate(mockComputationsBoxRetriever) shouldBe empty
      }
      "fail validation when CPQ1000 is true" in {
        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(true)))
        LEC01().validate(mockComputationsBoxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.required"))
      }
    }
    "when cars exist" when {
      val cars = List(Car(Some("LG64 RDO"), Some(26000), Some(12), Some(new LocalDate("2015-04-01")), Some(true)))
      "fail validation when CPQ1000 is false" in {
        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(false)))
        LEC01(cars).validate(mockComputationsBoxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.cannot.exist"))
      }

      "fail validation when CPQ1000 is empty" in {
        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(None))
        LEC01(cars).validate(mockComputationsBoxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.cannot.exist"))
      }

      "pass validation when CPQ1000 is true" in {
        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(true)))
        LEC01(cars).validate(mockComputationsBoxRetriever) shouldBe empty
      }
    }
  }
}
