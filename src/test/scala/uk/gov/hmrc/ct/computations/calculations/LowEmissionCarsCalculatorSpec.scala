/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.lowEmissionCars.{Car, LEC01}

case class TestCalculator() extends LowEmissionCarsCalculator

class LowEmissionCarsCalculatorSpec extends WordSpec with Matchers {

  private val calculator = TestCalculator()
 

  /**
   * the methods below might seem superfluous. But turning the parameters of Car into Options caused
   * such a mess that I tried
   */

  private def carReg(registrationNumber: String) = Some(registrationNumber)
  private val carIsNew = Some(true)
  private val carIsSecondHand = Some(false)
  private val registrationNumber = "B0SH 300"
  private def costOfCar(value: Int) = Some(value)
  private def emissionsOfCar(value: Int) = Some(value)
  private def dateCarWasPurchased(date: LocalDate) = Some(date)

  //This car list will give a Special Rates Pool value of 50.50
  val specialRatesCarList = LEC01(List(
    Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(666), dateOfPurchase = Some(new LocalDate("2014-01-31"))),
    Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(20), emissionsOfCar(1), dateOfPurchase = Some(new LocalDate("2014-01-31"))),
    Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(40), emissionsOfCar(777), dateOfPurchase = Some(new LocalDate("2014-01-31")))
  ))

  "getFYAPoolSum" should {

    "return correct pool for range1 car" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(123), dateOfPurchase = Some(new LocalDate("2009-03-31")))) shouldBe "MainRate"
    }


    "return correct pool for new range 2 car with <=110 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = Some(new LocalDate("2009-04-01")))) shouldBe "FYA"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = Some(new LocalDate("2013-03-31")))) shouldBe "FYA"
    }
    "return correct pool for new range 2 car with 111-160 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(111), dateOfPurchase = Some( new LocalDate("2009-04-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(160), dateOfPurchase = Some( new LocalDate("2009-04-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(111), dateOfPurchase = Some( new LocalDate("2013-03-31")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(160), dateOfPurchase = Some( new LocalDate("2013-03-31")))) shouldBe "MainRate"
    }
    "return correct pool for new range 2 car with >160 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(161), dateOfPurchase = Some( new LocalDate("2009-04-01")))) shouldBe "SpecialRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(161), dateOfPurchase = Some( new LocalDate("2013-03-31")))) shouldBe "SpecialRate"
    }
    "return correct pool for 2nd hand range 2 car with <=160 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(160), dateOfPurchase = Some( new LocalDate("2009-04-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(160), dateOfPurchase = Some( new LocalDate("2013-03-31")))) shouldBe "MainRate"
    }
    "return correct pool for 2nd hand range 2 car with >160 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(161), dateOfPurchase = Some( new LocalDate("2009-04-01")))) shouldBe "SpecialRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(161), dateOfPurchase = Some( new LocalDate("2013-03-31")))) shouldBe "SpecialRate"
    }


    "return correct pool for new range3 car with <=95 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(95), dateOfPurchase = Some( new LocalDate("2013-04-01")))) shouldBe "FYA"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(95), dateOfPurchase = Some( new LocalDate("2015-03-31")))) shouldBe "FYA"
    }
    "return correct pool for new range3 car with 96-130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(96), dateOfPurchase = Some( new LocalDate("2013-04-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(96), dateOfPurchase = Some( new LocalDate("2015-03-31")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some( new LocalDate("2013-04-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some( new LocalDate("2015-03-31")))) shouldBe "MainRate"
    }
    "return correct pool for new range3 car with >130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some( new LocalDate("2013-04-01")))) shouldBe "SpecialRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some( new LocalDate("2015-03-31")))) shouldBe "SpecialRate"
    }

    "return correct pool for 2nd hand range3 car with <=130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some( new LocalDate("2013-04-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some( new LocalDate("2015-03-31")))) shouldBe "MainRate"
    }
    "return correct pool for 2nd hand range3 car with >130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some( new LocalDate("2013-04-01")))) shouldBe "SpecialRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some( new LocalDate("2015-03-31")))) shouldBe "SpecialRate"
    }


    "return correct pool for new range4 car with <=75 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(75), dateOfPurchase = Some( new LocalDate("2015-04-01")))) shouldBe "FYA"
    }
    "return correct pool for new range4 car with 76-130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(76), dateOfPurchase = Some( new LocalDate("2015-04-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some( new LocalDate("2015-04-01")))) shouldBe "MainRate"
    }
    "return correct pool for new range4 car with >130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some( new LocalDate("2015-04-01")))) shouldBe "SpecialRate"
    }
    "return correct pool for 2nd had range4 car with <=130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some( new LocalDate("2015-04-01")))) shouldBe "MainRate"
    }
    "return correct pool for 2nd hand range4 car with >130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some( new LocalDate("2015-04-01")))) shouldBe "SpecialRate"
    }


    "return correct pool for new range4 car with <=75 emissions for the purchase date of 2018-03-31" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(75), dateOfPurchase = Some( new LocalDate("2018-03-31")))) shouldBe "FYA"
    }
    "return correct pool for new range4 car with 76-130 emissions for the purchase date of 2018-03-31" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(76), dateOfPurchase = Some( new LocalDate("2018-03-31")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some( new LocalDate("2018-03-31")))) shouldBe "MainRate"
    }
    "return correct pool for new range4 car with >130 emissions for the purchase date of 2018-03-31" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some( new LocalDate("2018-03-31")))) shouldBe "SpecialRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(150), dateOfPurchase = Some( new LocalDate("2019-04-25")))) shouldBe "SpecialRate"
    }
    "return correct pool for 2nd had range4 car with <=130 emissions for the purchase date of 2018-03-31" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(130), dateOfPurchase = Some( new LocalDate("2018-03-31")))) shouldBe "MainRate"
    }
    "return correct pool for 2nd hand range4 car with >130 emissions for the purchase date of 2018-03-31" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(131), dateOfPurchase = Some( new LocalDate("2018-03-31")))) shouldBe "SpecialRate"
    }

    "return correct pool for new range5 car with <=50 emissions for the purchase date of 2018-04-01" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(45), dateOfPurchase = Some( new LocalDate("2018-04-01")))) shouldBe "FYA"
    }
    "return correct pool for new range5 car with 51-110 emissions for the purchase date of 2018-04-01" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(60), dateOfPurchase = Some( new LocalDate("2018-04-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = Some( new LocalDate("2018-04-01")))) shouldBe "MainRate"
    }

    "return correct pool for 2nd had range5 car with <=110 emissions for the purchase date of 2018-04-01" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(100), dateOfPurchase = Some( new LocalDate("2018-04-01")))) shouldBe "MainRate"
    }
    "return correct pool for 2nd hand range5 car with >110 emissions for the purchase date of 2018-04-01" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(140), dateOfPurchase = Some( new LocalDate("2018-04-01")))) shouldBe "SpecialRate"
    }

    "return correct pool for new range5 car with <=50 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(49), dateOfPurchase = Some( new LocalDate("2018-06-01")))) shouldBe "FYA"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(50), dateOfPurchase = Some( new LocalDate("2018-06-01")))) shouldBe "FYA"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(30), dateOfPurchase = Some( new LocalDate("2019-06-02")))) shouldBe "FYA"

    }
    "return correct pool for new range5 car with 51-110 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(51), dateOfPurchase = Some( new LocalDate("2018-06-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(110), dateOfPurchase = Some( new LocalDate("2018-06-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(90), dateOfPurchase = Some( new LocalDate("2019-06-02")))) shouldBe "MainRate"
    }

    "return correct pool for 2nd had range5 car with <=110 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(109), dateOfPurchase = Some( new LocalDate("2018-06-01")))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(108), dateOfPurchase = Some( new LocalDate("2019-04-02")))) shouldBe "MainRate"
    }
    "return correct pool for 2nd hand range5 car with >110 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(111), dateOfPurchase = Some( new LocalDate("2018-06-01")))) shouldBe "SpecialRate"
      calculator.taxPoolForCar(Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(10), emissionsOfCar(120), dateOfPurchase = Some( new LocalDate("2019-05-06")))) shouldBe "SpecialRate"
    }

    "return x for fya eligible cars" in {
      val lec01 = LEC01(List(
        Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(1), dateOfPurchase = Some( new LocalDate("2014-01-31"))),
        Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(20), emissionsOfCar(666), dateOfPurchase = Some( new LocalDate("2014-01-31"))),
        Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(40), emissionsOfCar(1), dateOfPurchase = Some( new LocalDate("2014-01-31")))
      ))
      calculator.getFYAPoolSum(lec01) shouldBe 50
    }
    "return x for main rate pool cars" in {
      val lec01 = LEC01(List(
        Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(10), emissionsOfCar(129), dateOfPurchase = Some( new LocalDate("2014-01-31"))),
        Car(regNumber = carReg(registrationNumber), carIsNew, costOfCar(20), emissionsOfCar(666), dateOfPurchase = Some( new LocalDate("2014-01-31"))),
        Car(regNumber = carReg(registrationNumber), carIsSecondHand, costOfCar(40), emissionsOfCar(129), dateOfPurchase = Some( new LocalDate("2014-01-31")))
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
      Car(regNumber = Some("Car1"), carIsNew, costOfCar(100), emissionsOfCar(110), dateOfPurchase = Some( new LocalDate("2009-04-01"))),
      Car(regNumber = Some("Car2"), carIsSecondHand, costOfCar(111), emissionsOfCar(150), dateOfPurchase = Some( new LocalDate("2009-04-01"))),
      Car(regNumber = Some("Car3"), carIsNew, costOfCar(222), emissionsOfCar(180), dateOfPurchase = Some( new LocalDate("2009-04-01"))),
      Car(regNumber = Some("CAR16"), carIsSecondHand, costOfCar(333), emissionsOfCar(165), dateOfPurchase = Some( new LocalDate("2009-04-01"))),
      Car(regNumber = Some("CAR4"), carIsNew, costOfCar(444), emissionsOfCar(115), dateOfPurchase = Some( new LocalDate("2013-03-31"))),
      Car(regNumber = Some("CAR5"), carIsNew, costOfCar(555), emissionsOfCar(90), dateOfPurchase = Some( new LocalDate("2015-03-31"))),
      Car(regNumber = Some("CAR6"), carIsNew, costOfCar(666), emissionsOfCar(96), dateOfPurchase = Some( new LocalDate("2015-03-31"))),
      Car(regNumber = Some("CAR7"), carIsSecondHand, costOfCar(777), emissionsOfCar(130), dateOfPurchase = Some( new LocalDate("2015-03-31"))),
      Car(regNumber = Some("CAR8"), carIsNew, costOfCar(888), emissionsOfCar(140), dateOfPurchase = Some( new LocalDate("2015-03-31"))),
      Car(regNumber = Some("CAR17"), carIsSecondHand, costOfCar(999), emissionsOfCar(150), dateOfPurchase = Some( new LocalDate("2015-03-31"))),
      Car(regNumber = Some("CAR9"), carIsNew, costOfCar(1111), emissionsOfCar(75), dateOfPurchase = Some( new LocalDate("2015-04-01"))),
      Car(regNumber = Some("CAR10"), carIsNew, costOfCar(2222), emissionsOfCar(76), dateOfPurchase = Some( new LocalDate("2015-06-01"))),
      Car(regNumber = Some("CAR11"), carIsSecondHand, costOfCar(3333), emissionsOfCar(130), dateOfPurchase = Some( new LocalDate("2015-06-01"))),
      Car(regNumber = Some("CAR12"), carIsNew, costOfCar(4444), emissionsOfCar(131), dateOfPurchase = Some( new LocalDate("2015-06-01"))),
      Car(regNumber = Some("CAR18"), carIsSecondHand, costOfCar(5555), emissionsOfCar(131), dateOfPurchase = Some( new LocalDate("2015-06-01"))),
      Car(regNumber = Some("CAR13"), carIsNew, costOfCar(6666), emissionsOfCar(1), dateOfPurchase = Some( new LocalDate("2008-02-01"))),
      Car(regNumber = Some("CAR14"), carIsSecondHand, costOfCar(7777), emissionsOfCar(1), dateOfPurchase = Some( new LocalDate("2008-02-01"))),
      Car(regNumber = Some("CAR15"), carIsNew, costOfCar(8888), emissionsOfCar(600), dateOfPurchase = Some( new LocalDate("2008-02-01"))),
      Car(regNumber = Some("CAR19"), carIsSecondHand, costOfCar(9999), emissionsOfCar(600), dateOfPurchase = Some( new LocalDate("2008-02-01")))
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



}
