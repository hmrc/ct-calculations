/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.lowEmissionCars

import org.joda.time.LocalDate
import org.mockito.Mockito._
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.{CP1, CP2, CPQ1000, Car, LEC01}
import uk.gov.hmrc.ct.utils.UnitSpec

class LEC01Spec extends UnitSpec {

  "LEC01" should {

    val standardCar = Car("LG64 RDO", true, 26000, 12, LocalDate.now())

//    "when empty" when {
//      "pass validation when CPQ1000 is false" in {
//        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(false)))
//        LEC01().validate(mockComputationsBoxRetriever) shouldBe empty
//      }
//      "pass validation when CPQ1000 is empty" in {
//        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(None))
//        LEC01().validate(mockComputationsBoxRetriever) shouldBe empty
//      }
//      "fail validation when CPQ1000 is true" in {
//        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(true)))
//        LEC01().validate(mockComputationsBoxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.required"))
//      }
//    }
//    "when cars exist" when {
//      val cars = List(Car(Some("LG64 RDO"), Some(26000), Some(12), Some(new LocalDate("2015-04-01")), Some(true)))
//      "fail validation when CPQ1000 is false" in {
//        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(false)))
//        LEC01(cars).validate(mockComputationsBoxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.cannot.exist"))
//      }
//
//      "fail validation when CPQ1000 is empty" in {
//        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(None))
//        LEC01(cars).validate(mockComputationsBoxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.cannot.exist"))
//      }
//
//      "pass validation when CPQ1000 is true" in {
//        when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(true)))
//        LEC01(cars).validate(mockComputationsBoxRetriever) shouldBe empty
//      }
//    }
    "fail date of purchase validation" when {
      val today = LocalDate.now()
      val oneYearFromToday = today.plusYears(1)
      val dateOutOfRangeErrorMsg = "block.lowEmissionCar.dateOfPurchase.outOfRange"

      def shouldBeOutOfRangeError(car: Car) =
        LEC01(List(car)).validate(mockComputationsBoxRetriever) shouldBe
          Set(CtValidation(Some("LEC01E"), dateOutOfRangeErrorMsg)) // Would this be better stating the two dates it needs to fall between?

      when(mockComputationsBoxRetriever.cp1()) thenReturn CP1(today)
      when(mockComputationsBoxRetriever.cp2()) thenReturn CP2(oneYearFromToday)

      "the date of purchase is after the accounting period end date" in {
        val afterEndOfAccountingPeriod = oneYearFromToday.plusDays(1)
        val car = standardCar.copy(dateOfPurchase = afterEndOfAccountingPeriod)

        shouldBeOutOfRangeError(car)
      }

      "the date of purchase is before the accounting period start date" in {
        val beforeStartOfAccountingPeriod = today.minusDays(1)
        val car = standardCar.copy(dateOfPurchase = beforeStartOfAccountingPeriod)

        shouldBeOutOfRangeError(car)
      }
    }
  }
}
