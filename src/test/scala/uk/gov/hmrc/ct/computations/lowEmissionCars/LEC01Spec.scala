/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.lowEmissionCars

import org.joda.time.LocalDate
import org.mockito.Mockito._
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.{CP1, CP2, CPQ1000}
import uk.gov.hmrc.ct.utils.UnitSpec
import CarsHelper._
class LEC01Spec extends UnitSpec {

  "LEC01" should {

    val today = LocalDate.now()
    val oneYearFromToday = today.plusYears(1)

    val standardCar = Car(Some("LG64 RDO"), Some(true), Some(26000), Some(12), Some(today))
    val cars = List(standardCar)
    val carsId = Some(lec01BoxId)

        "when empty" when {
          "pass validation when CPQ1000 is false" in {
            when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(false)))

            LEC01().validate(mockComputationsBoxRetriever) shouldBe validationSuccess
          }
          "pass validation when CPQ1000 is empty" in {
            when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(None))

            LEC01().validate(mockComputationsBoxRetriever) shouldBe validationSuccess
          }
          "fail validation when CPQ1000 is true" in {
            when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(true)))

            LEC01().validate(mockComputationsBoxRetriever) shouldBe fieldRequiredError(lec01BoxId)
          }
        }
        "when cars exist" when {
          val cannotExistError = Set(CtValidation(carsId, "error.LEC01.cannot.exist"))

          "fail validation when CPQ1000 is false" in {
            when(mockComputationsBoxRetriever.cp1()) thenReturn CP1(today)
            when(mockComputationsBoxRetriever.cp2()) thenReturn CP2(oneYearFromToday)
            when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(false)))

            LEC01(cars).validate(mockComputationsBoxRetriever) shouldBe cannotExistError
          }

          "fail validation when CPQ1000 is empty" in {

            when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(None))
            LEC01(cars).validate(mockComputationsBoxRetriever) shouldBe cannotExistError
          }

          "pass validation when CPQ1000 is true" in {
            when(mockComputationsBoxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(true)))
            LEC01(cars).validate(mockComputationsBoxRetriever) shouldBe validationSuccess
          }
        }

    "fail vehicle registration number validation" when {
      "the user doesn't enter a registration number" in {
        val carNoRegNumber = standardCar.copy(regNumber = None)

        carError(carNoRegNumber, fieldRequiredError(registrationNumberId))
      }
    }

    "fail is the car new or second hand validation" when {
      "the user doesn't select a radio button" in {
        val carNoPurchasedState = standardCar.copy(isNew = None)

        carError(carNoPurchasedState, fieldRequiredError(isCarNewId))
      }
    }

    "fail price of the car validation" when {
      "the user doesn't enter a value" in {
        val carNoPrice = standardCar.copy(price = None)

        carError(carNoPrice, fieldRequiredError(priceId))
      }
    }

    "fail car emissions validation" when {
      "the user doesn't enter a value" in {
        val carNoPurchasedState = standardCar.copy(emissions = None)
        carError(carNoPurchasedState, fieldRequiredError(emissionsId))
      }
    }

    "fail date of purchase validation" when {
      val dateOutOfRangeErrorMsg = s"error.$dateOfPurchaseId.not.betweenInclusive"
      val dateFormat = "d MMMM yyyy"
      val outOfRangeError = Set(CtValidation(Some(dateOfPurchaseId), dateOutOfRangeErrorMsg,
        Some(List(today.toString(dateFormat), oneYearFromToday.toString(dateFormat)))))

      when(mockComputationsBoxRetriever.cp1()) thenReturn CP1(today)
      when(mockComputationsBoxRetriever.cp2()) thenReturn CP2(oneYearFromToday)

      "the date of purchase is after the accounting period end date" in {
        val afterEndOfAccountingPeriod = oneYearFromToday.plusDays(1)
        val car = standardCar.copy(dateOfPurchase = Some(afterEndOfAccountingPeriod))

        carError(car, outOfRangeError)
      }

      "the date of purchase is before the accounting period start date" in {
        val beforeStartOfAccountingPeriod = today.minusDays(1)
        val car = standardCar.copy(dateOfPurchase = Some(beforeStartOfAccountingPeriod))

        carError(car, outOfRangeError)
      }
      "when the user leaves the field blank" in {
        val carNoDate = standardCar.copy(dateOfPurchase = None)

        carError(carNoDate, fieldRequiredError(dateOfPurchaseId))
      }
    }
  }

  private def carError(car: Car, error: Set[CtValidation]) = car.validate(mockComputationsBoxRetriever) shouldBe error
}