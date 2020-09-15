/*
 * Copyright 2020 HM Revenue & Customs
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

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.lowEmissionCars.{Car, LEC01}

case class TestCalculator() extends LowEmissionCarsCalculator

class LowEmissionCarsCalculatorSpec extends WordSpec with Matchers {

//  private val calculator = TestCalculator()
//  private val carReg = Some(carReg)
//  private val carIsNew = Some(true)
//  private val carIsSecondHand = Some(false)
//
//  private def costOfCar(value: Int) = Some(value)
//  private def emissionsOfCar(value: Int) = Some(value)
//
//  //This car list will give a Special Rates Pool value of 50.50
//  val specialRatesCarList = LEC01(List(
//    Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(666), dateOfPurchase = new LocalDate("2014-01-31")),
//    Car(regNumber = carReg, carIsNew, costOfCar(20), emissionsOfCar(1), dateOfPurchase = new LocalDate("2014-01-31")),
//    Car(regNumber = carReg, carIsSecondHand, costOfCar(40), emissionsOfCar(777), dateOfPurchase = new LocalDate("2014-01-31"))
//  ))
//
//  "getFYAPoolSum" should {
//
//    "return correct pool for range1 car" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(123), dateOfPurchase = new LocalDate("2009-03-31"))) shouldBe "MainRate"
//    }
//
//
//    "return correct pool for new range 2 car with <=110 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "FYA"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "FYA"
//    }
//    "return correct pool for new range 2 car with 111-160 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(111), dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(160), dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(111), dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(160), dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "MainRate"
//    }
//    "return correct pool for new range 2 car with >160 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(161), dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "SpecialRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(161), dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "SpecialRate"
//    }
//    "return correct pool for 2nd hand range 2 car with <=160 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(160), dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(160), dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "MainRate"
//    }
//    "return correct pool for 2nd hand range 2 car with >160 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(161), dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "SpecialRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(161), dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "SpecialRate"
//    }
//
//
//    "return correct pool for new range3 car with <=95 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(95), dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "FYA"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(95), dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "FYA"
//    }
//    "return correct pool for new range3 car with 96-130 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(96), dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(96), dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "MainRate"
//    }
//    "return correct pool for new range3 car with >130 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "SpecialRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "SpecialRate"
//    }
//
//    "return correct pool for 2nd hand range3 car with <=130 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "MainRate"
//    }
//    "return correct pool for 2nd hand range3 car with >130 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "SpecialRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "SpecialRate"
//    }
//
//
//    "return correct pool for new range4 car with <=75 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(75), dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "FYA"
//    }
//    "return correct pool for new range4 car with 76-130 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(76), dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "MainRate"
//    }
//    "return correct pool for new range4 car with >130 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "SpecialRate"
//    }
//    "return correct pool for 2nd had range4 car with <=130 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "MainRate"
//    }
//    "return correct pool for 2nd hand range4 car with >130 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "SpecialRate"
//    }
//
//
//    "return correct pool for new range4 car with <=75 emissions for the purchase date of 2018-03-31" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(75), dateOfPurchase = new LocalDate("2018-03-31"))) shouldBe "FYA"
//    }
//    "return correct pool for new range4 car with 76-130 emissions for the purchase date of 2018-03-31" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(76), dateOfPurchase = new LocalDate("2018-03-31"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = new LocalDate("2018-03-31"))) shouldBe "MainRate"
//    }
//    "return correct pool for new range4 car with >130 emissions for the purchase date of 2018-03-31" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = new LocalDate("2018-03-31"))) shouldBe "SpecialRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(150), dateOfPurchase = new LocalDate("2019-04-25"))) shouldBe "SpecialRate"
//    }
//    "return correct pool for 2nd had range4 car with <=130 emissions for the purchase date of 2018-03-31" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = new LocalDate("2018-03-31"))) shouldBe "MainRate"
//    }
//    "return correct pool for 2nd hand range4 car with >130 emissions for the purchase date of 2018-03-31" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = new LocalDate("2018-03-31"))) shouldBe "SpecialRate"
//    }
//
//    "return correct pool for new range5 car with <=50 emissions for the purchase date of 2018-04-01" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissions = 45, dateOfPurchase = new LocalDate("2018-04-01"))) shouldBe "FYA"
//    }
//    "return correct pool for new range5 car with 51-110 emissions for the purchase date of 2018-04-01" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(60), dateOfPurchase = new LocalDate("2018-04-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = new LocalDate("2018-04-01"))) shouldBe "MainRate"
//    }
//
//    "return correct pool for 2nd had range5 car with <=110 emissions for the purchase date of 2018-04-01" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(100), dateOfPurchase = new LocalDate("2018-04-01"))) shouldBe "MainRate"
//    }
//    "return correct pool for 2nd hand range5 car with >110 emissions for the purchase date of 2018-04-01" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(140), dateOfPurchase = new LocalDate("2018-04-01"))) shouldBe "SpecialRate"
//    }
//
//    "return correct pool for new range5 car with <=50 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(49), dateOfPurchase = new LocalDate("2018-06-01"))) shouldBe "FYA"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(50), dateOfPurchase = new LocalDate("2018-06-01"))) shouldBe "FYA"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(30), dateOfPurchase = new LocalDate("2019-06-02"))) shouldBe "FYA"
//
//    }
//    "return correct pool for new range5 car with 51-110 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(51), dateOfPurchase = new LocalDate("2018-06-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = new LocalDate("2018-06-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(90), dateOfPurchase = new LocalDate("2019-06-02"))) shouldBe "MainRate"
//    }
//
//    "return correct pool for 2nd had range5 car with <=110 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(109), dateOfPurchase = new LocalDate("2018-06-01"))) shouldBe "MainRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(108), dateOfPurchase = new LocalDate("2019-04-02"))) shouldBe "MainRate"
//    }
//    "return correct pool for 2nd hand range5 car with >110 emissions" in {
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(111), dateOfPurchase = new LocalDate("2018-06-01"))) shouldBe "SpecialRate"
//      calculator.taxPoolForCar(Car(regNumber = carReg, carIsSecondHand, costOfCar(10), emissionsOfCar(120), dateOfPurchase = new LocalDate("2019-05-06"))) shouldBe "SpecialRate"
//    }
//
//    "return x for fya eligible cars" in {
//      val lec01 = LEC01(List(
//        Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(1), dateOfPurchase = new LocalDate("2014-01-31")),
//        Car(regNumber = carReg, carIsNew, costOfCar(20), emissionsOfCar(666), dateOfPurchase = new LocalDate("2014-01-31")),
//        Car(regNumber = carReg, carIsNew, costOfCar(40), emissionsOfCar(1), dateOfPurchase = new LocalDate("2014-01-31"))
//      ))
//      calculator.getFYAPoolSum(lec01) shouldBe 50
//    }
//    "return x for main rate pool cars" in {
//      val lec01 = LEC01(List(
//        Car(regNumber = carReg, carIsNew, costOfCar(10), emissionsOfCar(129), dateOfPurchase = new LocalDate("2014-01-31")),
//        Car(regNumber = carReg, carIsNew, costOfCar(20), emissionsOfCar(666), dateOfPurchase = new LocalDate("2014-01-31")),
//        Car(regNumber = carReg, carIsSecondHand, costOfCar(40), emissionsOfCar(129), dateOfPurchase = new LocalDate("2014-01-31"))
//      ))
//      calculator.getMainRatePoolSum(lec01) shouldBe 50
//    }
//    "return x for special rate pool cars" in {
//      calculator.getSpecialRatePoolSum(specialRatesCarList) shouldBe 50
//    }
//    "not blow up with empty list of cars" in {
//      val lec01 = LEC01(List.empty)
//      calculator.getSpecialRatePoolSum(lec01) shouldBe 0
//    }
//  }
//
//
//  "pass the CATO-2525 jira acceptance criteria" in {
//    val lec01 = LEC01(List(
//      Car(regNumber = "CAR1", carIsNew, costOfCar(100), emissionsOfCar(110), dateOfPurchase = new LocalDate("2009-04-01")),
//      Car(regNumber = "CAR2", carIsSecondHand, price = 111, emissionsOfCar(150), dateOfPurchase = new LocalDate("2009-04-01")),
//      Car(regNumber = "CAR3", carIsNew, price = 222, emissionsOfCar(180), dateOfPurchase = new LocalDate("2009-04-01")),
//      Car(regNumber = "CAR16", carIsSecondHand, price = 333, emissionsOfCar(165), dateOfPurchase = new LocalDate("2009-04-01")),
//      Car(regNumber = "CAR4", carIsNew, price = 444, emissionsOfCar(115), dateOfPurchase = new LocalDate("2013-03-31")),
//      Car(regNumber = "CAR5", carIsNew, price = 555, emissionsOfCar(90), dateOfPurchase = new LocalDate("2015-03-31")),
//      Car(regNumber = "CAR6", carIsNew, price = 666, emissionsOfCar(96), dateOfPurchase = new LocalDate("2015-03-31")),
//      Car(regNumber = "CAR7", carIsSecondHand, price = 777, emissionsOfCar(130), dateOfPurchase = new LocalDate("2015-03-31")),
//      Car(regNumber = "CAR8", carIsNew, price = 888, emissionsOfCar(140), dateOfPurchase = new LocalDate("2015-03-31")),
//      Car(regNumber = "CAR17", carIsSecondHand, price = 999, emissionsOfCar(150), dateOfPurchase = new LocalDate("2015-03-31")),
//      Car(regNumber = "CAR9", carIsNew, price = 1111, emissionsOfCar(75), dateOfPurchase = new LocalDate("2015-04-01")),
//      Car(regNumber = "CAR10", carIsNew, price = 2222, emissionsOfCar(76), dateOfPurchase = new LocalDate("2015-06-01")),
//      Car(regNumber = "CAR11", carIsSecondHand, price = 3333, emissionsOfCar(130), dateOfPurchase = new LocalDate("2015-06-01")),
//      Car(regNumber = "CAR12", carIsNew, price = 4444, emissionsOfCar(131), dateOfPurchase = new LocalDate("2015-06-01")),
//      Car(regNumber = "CAR18", carIsSecondHand, price = 5555, emissionsOfCar(131), dateOfPurchase = new LocalDate("2015-06-01")),
//      Car(regNumber = "CAR13", carIsNew, price = 6666, emissionsOfCar(1), dateOfPurchase = new LocalDate("2008-02-01")),
//      Car(regNumber = "CAR14", carIsSecondHand, price = 7777, emissionsOfCar(1), dateOfPurchase = new LocalDate("2008-02-01")),
//      Car(regNumber = "CAR15", carIsNew, price = 8888, emissionsOfCar(600), dateOfPurchase = new LocalDate("2008-02-01")),
//      Car(regNumber = "CAR19", carIsSecondHand, price = 9999, emissionsOfCar(600), dateOfPurchase = new LocalDate("2008-02-01"))
//    ))
//    calculator.getFYAPoolSum(lec01) shouldBe 1766
//    calculator.getMainRatePoolSum(lec01) shouldBe 40883
//    calculator.getSpecialRatePoolSum(lec01) shouldBe 12441
//  }
//
//
//  "calculateSpecialRatePoolBalancingCharge" should {
//
//    "calculate CP670 where cpq8 is false" in {
//      val result = calculator.calculateSpecialRatePoolBalancingCharge(
//        cpq8 = CPQ8(Some(false)),
//        cp666 = CP666(Some(1)),
//        cp667 = CP667(Some(20)),
//        cpAux3 = CPAux3(3)
//      )
//
//      result shouldBe CP670(Some(16))
//    }
//
//    "calculate CP670 where cpq8 is true" in {
//      val result = calculator.calculateSpecialRatePoolBalancingCharge(
//        cpq8 = CPQ8(Some(true)),
//        cp666 = CP666(Some(1)),
//        cp667 = CP667(Some(20)),
//        cpAux3 = CPAux3(3)
//      )
//
//      result shouldBe CP670(Some(0))
//    }
//
//    "calculate CP670 where cp667<=cp666+cpAux3" in {
//      val result = calculator.calculateSpecialRatePoolBalancingCharge(
//        cpq8 = CPQ8(Some(false)),
//        cp666 = CP666(Some(1)),
//        cp667 = CP667(Some(4)),
//        cpAux3 = CPAux3(3)
//      )
//
//      result shouldBe CP670(Some(0))
//    }
//
//  }
//
//  "calculateSpecialRatePoolWrittenDownValueCarriedForward" should {
//
//    "calculate CP669 where cpq8 is false" in {
//      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(
//        cpq8 = CPQ8(Some(false)),
//        cp666 = CP666(Some(10)),
//        cp667 = CP667(Some(4)),
//        cp668 = CP668(Some(3)),
//        cp670 = CP670(Some(0)),
//        cpAux3 = CPAux3(2)
//      )
//
//      result shouldBe CP669(Some(5))
//    }
//
//    "calculate CP669 where cpq8 is true" in {
//      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(
//        cpq8 = CPQ8(Some(true)),
//        cp666 = CP666(Some(10)),
//        cp667 = CP667(Some(4)),
//        cp668 = CP668(Some(3)),
//        cp670 = CP670(Some(0)),
//        cpAux3 = CPAux3(2)
//      )
//
//      result shouldBe CP669(Some(0))
//    }
//
//    "calculate CP669 where result is -ve value" in {
//      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(
//        cpq8 = CPQ8(Some(false)),
//        cp666 = CP666(Some(10)),
//        cp667 = CP667(Some(40)),
//        cp668 = CP668(Some(3)),
//        cp670 = CP670(Some(0)),
//        cpAux3 = CPAux3(2)
//      )
//
//      result shouldBe CP669(Some(0))
//    }
//
//  }
//
//  "disposalsLessThanSpecialRatePool" should {
//
//    "return true if CP667 is less than CP666 + SpecialRatePool (==50)" in {
//      val result = calculator.disposalsLessThanSpecialRatePool(specialRatesCarList,
//        cp666 = CP666(Some(51)),
//        cp667 = CP667(Some(100))
//      )
//
//      result shouldBe true
//    }
//
//    "return false if CP667 not less than CP666 + SpecialRatePool (==50)" in {
//      val result = calculator.disposalsLessThanSpecialRatePool(specialRatesCarList,
//        cp666 = CP666(Some(50)),
//        cp667 = CP667(Some(100))
//      )
//
//      result shouldBe false
//    }
//  }
//
//  "disposalsLessThanMainRatePool" should {
//
//    "return true if CP672 is less than cp78 + cp82 + MainRatePool (==50)" in {
//      val result = calculator.disposalsLessThanMainRatePool(specialRatesCarList,
//        cp78 = CP78(Some(51)),
//        cp82 = CP82(Some(50)),
//        cp672 = CP672(Some(100))
//      )
//
//      result shouldBe true
//    }
//
//    "return false if CP672 is not less than cp78 + cp82 + MainRatePool (==50)" in {
//      val result = calculator.disposalsLessThanMainRatePool(specialRatesCarList,
//        cp78 = CP78(Some(50)),
//        cp82 = CP82(Some(50)),
//        cp672 = CP672(Some(100))
//      )
//
//      result shouldBe false
//    }
//  }
//


}
