/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.computations.calculations

import java.time.LocalDate
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.Assertion
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.lowEmissionCars.{Car, LEC01}

case class TestCalculator() extends LowEmissionCarsCalculator

class LowEmissionCarsCalculatorSpec extends AnyWordSpec with Matchers with LowEmissionCarsCalculator {

  private val calculator = TestCalculator()

  /**
   * The methods below might seem superfluous. But turning the parameters of Car into Options caused
   * such a mess that I've tried to reduce the amount of effort that would go into making future changes.
   */

  private def carReg(registrationNumber: String) = Some(registrationNumber)
  private val carIsNew = Some(true)
  private val carIsSecondHand = Some(false)
  private val registrationNumber = "B0SH 300"
  private def costOfCar(value: Int) = Some(value)
  private def emissionsOfCar(value: Int) = Some(value)
  private def carBelongsToThisTaxPool(car: Car, taxPool: LowEmissionCarRate) = calculator.taxPoolForCar(car) shouldBe taxPool
  private val exampleCar = Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(123), dateOfPurchase = Some(LocalDate.parse("2009-03-31")))

  //This car list will give a Special Rates Pool value of 50.50
  val specialRatesCarList: LEC01 = LEC01(List(
    Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(666), dateOfPurchase = Some(LocalDate.parse("2014-01-31"))),
    Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(20), emissionsOfCar(1), dateOfPurchase = Some(LocalDate.parse("2014-01-31"))),
    Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(40), emissionsOfCar(777), dateOfPurchase = Some(LocalDate.parse("2014-01-31")))
  ))

  "getFYAPoolSum" should {

      "return correct pool for range1 car" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(123), dateOfPurchase = Some(LocalDate.parse("2009-03-31")))) shouldBe MainRate
      }

    "return correct pool for a range 2 car" when {
      "the car is new and has <=110 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = Some(LocalDate.parse("2009-04-01")))) shouldBe FYA
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = Some(LocalDate.parse("2013-03-31")))) shouldBe FYA
      }
      "the car is new and has 111-160 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(111), dateOfPurchase = Some(LocalDate.parse("2009-04-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(160), dateOfPurchase = Some(LocalDate.parse("2009-04-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(111), dateOfPurchase = Some(LocalDate.parse("2013-03-31")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(160), dateOfPurchase = Some(LocalDate.parse("2013-03-31")))) shouldBe MainRate
      }
      "the car is new and has >160 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(161), dateOfPurchase = Some(LocalDate.parse("2009-04-01")))) shouldBe SpecialRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(161), dateOfPurchase = Some(LocalDate.parse("2013-03-31")))) shouldBe SpecialRate
      }
      "the car is 2nd hand and has <=160 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(160), dateOfPurchase = Some(LocalDate.parse("2009-04-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(160), dateOfPurchase = Some(LocalDate.parse("2013-03-31")))) shouldBe MainRate
      }
      "the car is 2nd hand and has >160 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(161), dateOfPurchase = Some(LocalDate.parse("2009-04-01")))) shouldBe SpecialRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(161), dateOfPurchase = Some(LocalDate.parse("2013-03-31")))) shouldBe SpecialRate
      }
    }

    "return correct pool for a range 2 car" when {
      "the car is new and has <=95 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(95), dateOfPurchase = Some(LocalDate.parse("2013-04-01")))) shouldBe FYA
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(95), dateOfPurchase = Some(LocalDate.parse("2015-03-31")))) shouldBe FYA
      }
      "the car is new and has 96-130 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(96), dateOfPurchase = Some(LocalDate.parse("2013-04-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(96), dateOfPurchase = Some(LocalDate.parse("2015-03-31")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some(LocalDate.parse("2013-04-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some(LocalDate.parse("2015-03-31")))) shouldBe MainRate
      }
      "the car is new and has >130 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some(LocalDate.parse("2013-04-01")))) shouldBe SpecialRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some(LocalDate.parse("2015-03-31")))) shouldBe SpecialRate
      }

      "the car is 2nd hand and has <=130 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some(LocalDate.parse("2013-04-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some(LocalDate.parse("2015-03-31")))) shouldBe MainRate
      }
      "the car is 2nd hand and has >130 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some(LocalDate.parse("2013-04-01")))) shouldBe SpecialRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some(LocalDate.parse("2015-03-31")))) shouldBe SpecialRate
      }
    }

    "return correct pool for a range 4 car" when {
      "the car is new and has <=75 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(75), dateOfPurchase = Some(LocalDate.parse("2015-04-01")))) shouldBe FYA
      }
      "the car is new and has 76-130 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(76), dateOfPurchase = Some(LocalDate.parse("2015-04-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some(LocalDate.parse("2015-04-01")))) shouldBe MainRate
      }
      "the car is new and has >130 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some(LocalDate.parse("2015-04-01")))) shouldBe SpecialRate
      }
      "the car is new and haswith <=130 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some(LocalDate.parse("2015-04-01")))) shouldBe MainRate
      }
      "the car is new and has with >130 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some(LocalDate.parse("2015-04-01")))) shouldBe SpecialRate
      }

      "the car is new and has <=75 emissions for the purchase date of 2018-03-31" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(75), dateOfPurchase = Some(LocalDate.parse("2018-03-31")))) shouldBe FYA
      }
      "the car is new and has 76-130 emissions for the purchase date of 2018-03-31" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(76), dateOfPurchase = Some(LocalDate.parse("2018-03-31")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some(LocalDate.parse("2018-03-31")))) shouldBe MainRate
      }
      "the car is new and has >130 emissions for the purchase date of 2018-03-31" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some(LocalDate.parse("2018-03-31")))) shouldBe SpecialRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(150), dateOfPurchase = Some(LocalDate.parse("2019-04-25")))) shouldBe SpecialRate
      }
      "the is 2nd hand and has <=130 emissions for the purchase date of 2018-03-31" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some(LocalDate.parse("2018-03-31")))) shouldBe MainRate
      }
      "the is 2nd hand and has >130 emissions for the purchase date of 2018-03-31" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some(LocalDate.parse("2018-03-31")))) shouldBe SpecialRate
      }
    }

    "return correct pool for a range 5 car" when {
      "the car is new and has <=50 emissions for the purchase date of 2018-04-01" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(45), dateOfPurchase = Some(LocalDate.parse("2018-04-01")))) shouldBe FYA
      }
      "the car is new and has 51-110 emissions for the purchase date of 2018-04-01" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(60), dateOfPurchase = Some(LocalDate.parse("2018-04-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = Some(LocalDate.parse("2018-04-01")))) shouldBe MainRate
      }

      "the car is 2nd hand and has <=110 emissions for the purchase date of 2018-04-01" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(100), dateOfPurchase = Some(LocalDate.parse("2018-04-01")))) shouldBe MainRate
      }
      "the car is 2nd hand and has >110 emissions for the purchase date of 2018-04-01" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(140), dateOfPurchase = Some(LocalDate.parse("2018-04-01")))) shouldBe SpecialRate
      }

      "the car is new and has <=50 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(49), dateOfPurchase = Some(LocalDate.parse("2018-06-01")))) shouldBe FYA
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(50), dateOfPurchase = Some(LocalDate.parse("2018-06-01")))) shouldBe FYA
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(30), dateOfPurchase = Some(LocalDate.parse("2019-06-02")))) shouldBe FYA

      }
      "the car is new and has 51-110 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(51), dateOfPurchase = Some(LocalDate.parse("2018-06-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = Some(LocalDate.parse("2018-06-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(90), dateOfPurchase = Some(LocalDate.parse("2019-06-02")))) shouldBe MainRate
      }

      "the car is 2nd hand and has <=110 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(109), dateOfPurchase = Some(LocalDate.parse("2018-06-01")))) shouldBe MainRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(108), dateOfPurchase = Some(LocalDate.parse("2019-04-02")))) shouldBe MainRate
      }
      "the car is 2nd hand and has >110 emissions" in {
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(111), dateOfPurchase = Some(LocalDate.parse("2018-06-01")))) shouldBe SpecialRate
        calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(120), dateOfPurchase = Some(LocalDate.parse("2019-05-06")))) shouldBe SpecialRate
      }
    }

    "return correct pool for a range 6 car, if the car is purchased after 2021-03-31 but before 2025-04-01" when {
      val firstHandCar = Car(
        carReg(registrationNumber),
        carIsNew,
        costOfCar(10),
        emissionsOfCar(0),
        dateOfPurchase = Some(LocalDate.parse("2021-04-01")))

      val secondHandCar = firstHandCar.copy(isNew = carIsSecondHand)

      "the car is new, has 0 emissions then the car should be in FYA" in {
        carBelongsToThisTaxPool(firstHandCar, FYA)
      }

      "the car is new, has emissions <= 50 then the car should be in Main Rate" in {
        val car = firstHandCar.copy(emissions = emissionsOfCar(45))
        carBelongsToThisTaxPool(car, MainRate)
      }

      "the car is 2nd hand, has 0 emissions then the car should be in Main Rate" in {
        carBelongsToThisTaxPool(secondHandCar, MainRate)
      }

      "the car is second hand, has emissions <= 50  then the car should be in Main Rate" in {
        val car = secondHandCar.copy(emissions = emissionsOfCar(45))
        carBelongsToThisTaxPool(car, MainRate)
      }

      "the car is new, has emissions > 50 then the car should be in Special Rate" in {
        val car = firstHandCar.copy(emissions = emissionsOfCar(55))
        carBelongsToThisTaxPool(car, SpecialRate)
      }

      "the car is second hand, has emissions > 50 then the car should be in Special Rate" in {
        val car = secondHandCar.copy(emissions = emissionsOfCar(55))
        carBelongsToThisTaxPool(car, SpecialRate)
      }
    }

    "return correct pool for a range 7 car, if the car is purchased on or after 2025-04-01" when {
      val dateAfterRange6 = LocalDate.parse("2025-04-01")
      val zeroEmissions = 0
      val specialRateEmissions = 51

      def car(isNew: Boolean, emissions: Int) = Car(
        carReg(registrationNumber),
        Some(isNew),
        costOfCar(1),
        emissionsOfCar(emissions),
        Some(dateAfterRange6)
      )

      "the car is new, has emissions <= 50 then the car should be in Main Rate" in {
        val newCar = car(true, zeroEmissions)
        carBelongsToThisTaxPool(newCar, MainRate)
      }
      "the car is second hand, has emissions <= 50 then the car should be Main Rate" in {
        val secondHandCar = car(false, zeroEmissions)
        carBelongsToThisTaxPool(secondHandCar, MainRate)
      }

      "the car is new, has emissions <= 50 then the car should be in Special Rate" in {
        val newCar = car(true, specialRateEmissions)
        carBelongsToThisTaxPool(newCar, SpecialRate)
      }
      "the car is second hand, has emissions <= 50 then the car should be in Special Rate" in {
        val secondHandCar = car(false, specialRateEmissions)
        carBelongsToThisTaxPool(secondHandCar, SpecialRate)
      }
    }

    "return x for fya eligible cars" in {
      val lec01 = LEC01(List(
        Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(1), dateOfPurchase = Some( LocalDate.parse("2014-01-31"))),
        Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(20), emissionsOfCar(666), dateOfPurchase = Some( LocalDate.parse("2014-01-31"))),
        Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(40), emissionsOfCar(1), dateOfPurchase = Some( LocalDate.parse("2014-01-31")))
      ))
      calculator.getFYAPoolSum(lec01) shouldBe 50
    }
    "return x for main rate pool cars" in {
      val lec01 = LEC01(List(
        Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(129), dateOfPurchase = Some( LocalDate.parse("2014-01-31"))),
        Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(20), emissionsOfCar(666), dateOfPurchase = Some( LocalDate.parse("2014-01-31"))),
        Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(40), emissionsOfCar(129), dateOfPurchase = Some( LocalDate.parse("2014-01-31")))
      ))
      calculator.getMainRatePoolSum(lec01) shouldBe 50
    }
    "return x for special rate pool cars" in {
      calculator.getSpecialRatePoolSum(specialRatesCarList) shouldBe 50
    }
    "not blow up with empty list of cars" in {
      val lec01 = LEC01(List.empty)
      calculator.getSpecialRatePoolSum(lec01) shouldBe 0
    }
  }


  "pass the CATO-2525 jira acceptance criteria" in {
    val lec01 = LEC01(List(
      Car(regNumber = Some("Car1"), carIsNew, costOfCar(100), emissionsOfCar(110), dateOfPurchase = Some( LocalDate.parse("2009-04-01"))),
      Car(regNumber = Some("Car2"), carIsSecondHand, costOfCar(111), emissionsOfCar(150), dateOfPurchase = Some( LocalDate.parse("2009-04-01"))),
      Car(regNumber = Some("Car3"), carIsNew, costOfCar(222), emissionsOfCar(180), dateOfPurchase = Some( LocalDate.parse("2009-04-01"))),
      Car(regNumber = Some("CAR16"), carIsSecondHand, costOfCar(333), emissionsOfCar(165), dateOfPurchase = Some( LocalDate.parse("2009-04-01"))),
      Car(regNumber = Some("CAR4"), carIsNew, costOfCar(444), emissionsOfCar(115), dateOfPurchase = Some( LocalDate.parse("2013-03-31"))),
      Car(regNumber = Some("CAR5"), carIsNew, costOfCar(555), emissionsOfCar(90), dateOfPurchase = Some( LocalDate.parse("2015-03-31"))),
      Car(regNumber = Some("CAR6"), carIsNew, costOfCar(666), emissionsOfCar(96), dateOfPurchase = Some( LocalDate.parse("2015-03-31"))),
      Car(regNumber = Some("CAR7"), carIsSecondHand, costOfCar(777), emissionsOfCar(130), dateOfPurchase = Some( LocalDate.parse("2015-03-31"))),
      Car(regNumber = Some("CAR8"), carIsNew, costOfCar(888), emissionsOfCar(140), dateOfPurchase = Some( LocalDate.parse("2015-03-31"))),
      Car(regNumber = Some("CAR17"), carIsSecondHand, costOfCar(999), emissionsOfCar(150), dateOfPurchase = Some( LocalDate.parse("2015-03-31"))),
      Car(regNumber = Some("CAR9"), carIsNew, costOfCar(1111), emissionsOfCar(75), dateOfPurchase = Some( LocalDate.parse("2015-04-01"))),
      Car(regNumber = Some("CAR10"), carIsNew, costOfCar(2222), emissionsOfCar(76), dateOfPurchase = Some( LocalDate.parse("2015-06-01"))),
      Car(regNumber = Some("CAR11"), carIsSecondHand, costOfCar(3333), emissionsOfCar(130), dateOfPurchase = Some( LocalDate.parse("2015-06-01"))),
      Car(regNumber = Some("CAR12"), carIsNew, costOfCar(4444), emissionsOfCar(131), dateOfPurchase = Some( LocalDate.parse("2015-06-01"))),
      Car(regNumber = Some("CAR18"), carIsSecondHand, costOfCar(5555), emissionsOfCar(131), dateOfPurchase = Some( LocalDate.parse("2015-06-01"))),
      Car(regNumber = Some("CAR13"), carIsNew, costOfCar(6666), emissionsOfCar(1), dateOfPurchase = Some( LocalDate.parse("2008-02-01"))),
      Car(regNumber = Some("CAR14"), carIsSecondHand, costOfCar(7777), emissionsOfCar(1), dateOfPurchase = Some( LocalDate.parse("2008-02-01"))),
      Car(regNumber = Some("CAR15"), carIsNew, costOfCar(8888), emissionsOfCar(600), dateOfPurchase = Some( LocalDate.parse("2008-02-01"))),
      Car(regNumber = Some("CAR19"), carIsSecondHand, costOfCar(9999), emissionsOfCar(600), dateOfPurchase = Some( LocalDate.parse("2008-02-01")))
    ))
    calculator.getFYAPoolSum(lec01) shouldBe 1766
    calculator.getMainRatePoolSum(lec01) shouldBe 40883
    calculator.getSpecialRatePoolSum(lec01) shouldBe 12441
  }


  "calculateSpecialRatePoolBalancingCharge" should {

    "calculate CP670 where cpq8 is false" in {
      val result = calculator.calculateSpecialRatePoolBalancingCharge(
        cpq8 = CPQ8(Some(false)),
        cp666 = CP666(Some(1)),
        cp667 = CP667(Some(20)),
        cpAux3 = CPAux3(3)
      )

      result shouldBe CP670(Some(16))
    }

    "calculate CP670 where cpq8 is true" in {
      val result = calculator.calculateSpecialRatePoolBalancingCharge(
        cpq8 = CPQ8(Some(true)),
        cp666 = CP666(Some(1)),
        cp667 = CP667(Some(20)),
        cpAux3 = CPAux3(3)
      )

      result shouldBe CP670(Some(0))
    }

    "calculate CP670 where cp667<=cp666+cpAux3" in {
      val result = calculator.calculateSpecialRatePoolBalancingCharge(
        cpq8 = CPQ8(Some(false)),
        cp666 = CP666(Some(1)),
        cp667 = CP667(Some(4)),
        cpAux3 = CPAux3(3)
      )

      result shouldBe CP670(Some(0))
    }

  }

  "calculateSpecialRatePoolWrittenDownValueCarriedForward" should {

    "calculate CP669 where cpq8 is false" in {
      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(
        cpq8 = CPQ8(Some(false)),
        cp666 = CP666(Some(10)),
        cp667 = CP667(Some(4)),
        cp668 = CP668(Some(3)),
        cp670 = CP670(Some(0)),
        cpAux3 = CPAux3(2)
      )

      result shouldBe CP669(Some(5))
    }

    "calculate CP669 where cpq8 is true" in {
      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(
        cpq8 = CPQ8(Some(true)),
        cp666 = CP666(Some(10)),
        cp667 = CP667(Some(4)),
        cp668 = CP668(Some(3)),
        cp670 = CP670(Some(0)),
        cpAux3 = CPAux3(2)
      )

      result shouldBe CP669(Some(0))
    }

    "calculate CP669 where result is -ve value" in {
      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(
        cpq8 = CPQ8(Some(false)),
        cp666 = CP666(Some(10)),
        cp667 = CP667(Some(40)),
        cp668 = CP668(Some(3)),
        cp670 = CP670(Some(0)),
        cpAux3 = CPAux3(2)
      )

      result shouldBe CP669(Some(0))
    }

  }

  "disposalsLessThanSpecialRatePool" should {

    "return true if CP667 is less than CP666 + SpecialRatePool (==50)" in {
      val result = calculator.disposalsLessThanSpecialRatePool(specialRatesCarList,
        cp666 = CP666(Some(51)),
        cp667 = CP667(Some(100))
      )

      result shouldBe true
    }

    "return false if CP667 not less than CP666 + SpecialRatePool (==50)" in {
      val result = calculator.disposalsLessThanSpecialRatePool(specialRatesCarList,
        cp666 = CP666(Some(50)),
        cp667 = CP667(Some(100))
      )

      result shouldBe false
    }
  }

  "disposalsLessThanMainRatePool" should {

    "return true if CP672 is less than cp78 + cp82 + MainRatePool (==50)" in {
      val result = calculator.disposalsLessThanMainRatePool(specialRatesCarList,
        cp78 = CP78(Some(51)),
        cp82 = CP82(Some(50)),
        cp672 = CP672(Some(100))
      )

      result shouldBe true
    }

    "return false if CP672 is not less than cp78 + cp82 + MainRatePool (==50)" in {
      val result = calculator.disposalsLessThanMainRatePool(specialRatesCarList,
        cp78 = CP78(Some(50)),
        cp82 = CP82(Some(50)),
        cp672 = CP672(Some(100))
      )

      result shouldBe false
    }
  }

  "ErrorState" should {
    "be returned from the taxPoolForCar method" when {
      "dateOfPurchase hasn't been entered by the user" in {
        val carWithNoDOP = exampleCar.copy(dateOfPurchase = None)
        val result = calculator.taxPoolForCar(carWithNoDOP)

        result shouldBe ErrorState
      }
    }
    "be returned from the taxPoolForCar method when the supplied car is missing its emissions or whether the car is new" when {
      "dateOfPurchase is between 1 April 2009 and the 31 April 2013" in {
        errorStateTest("2010-01-31")
      }
      "dateOfPurchase is between 1 April 2013 and the 31 April 2015" in {
        errorStateTest("2014-01-31")
      }
      "dateOfPurchase is between 1 April 2015 and the 31 March 2018" in {
        errorStateTest("2017-01-31")
      }
      "dateOfPurchase is between 1 April 2018 and the 31 March 2021" in {
        errorStateTest("2020-01-31")
      }
      "dateOfPurchase is between 1 April 2021 and the 31 March 2025" in {
        errorStateTest("2022-01-31")
      }
      "dateOfPurchase is after 1 March 2025" in {
        errorStateTest("2026-01-31")
      }
    }
  }

  private def errorStateTest(localDateString: String): Assertion = {
    val notNewCar = exampleCar.copy(dateOfPurchase = Some(LocalDate.parse(localDateString)), isNew = None)
    val noEmissionsCar = exampleCar.copy(dateOfPurchase = Some(LocalDate.parse(localDateString)), emissions = None)

    val result1 = calculator.taxPoolForCar(notNewCar)
    val result2 = calculator.taxPoolForCar(noEmissionsCar)

    result1 shouldBe ErrorState
    result2 shouldBe ErrorState
  }
}
