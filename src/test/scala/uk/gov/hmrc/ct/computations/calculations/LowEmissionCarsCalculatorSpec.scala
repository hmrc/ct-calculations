package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._

case class TestCalculator() extends LowEmissionCarsCalculator

class LowEmissionCarsCalculatorSpec extends WordSpec with Matchers {

  val calculator = TestCalculator()

  //This car list will give a Special Rates Pool value of 50.50
  val specialRatesCarList = LEC01(List(
    Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 666, dateOfPurchase = new LocalDate("2014-01-31")),
    Car(regNumber = "XYZ123A", isNew = true, price = 20, emissions = 1, dateOfPurchase = new LocalDate("2014-01-31")),
    Car(regNumber = "XYZ123A", isNew = false, price = 40, emissions = 777, dateOfPurchase = new LocalDate("2014-01-31"))
  ))

  "getFYAPoolSum" should {

    "return correct pool for range1 car" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 123, dateOfPurchase = new LocalDate("2009-03-31"))) shouldBe "MainRate"
    }


    "return correct pool for new range 2 car with <=110 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 110, dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "FYA"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 110, dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "FYA"
    }
    "return correct pool for new range 2 car with 111-160 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 111, dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 160, dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 111, dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 160, dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "MainRate"
    }
    "return correct pool for new range 2 car with >160 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 161, dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "SpecialRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 161, dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "SpecialRate"
    }
    "return correct pool for 2nd hand range 2 car with <=160 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = false, price = 10, emissions = 160, dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = false, price = 10, emissions = 160, dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "MainRate"
    }
    "return correct pool for 2nd hand range 2 car with >160 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = false, price = 10, emissions = 161, dateOfPurchase = new LocalDate("2009-04-01"))) shouldBe "SpecialRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = false, price = 10, emissions = 161, dateOfPurchase = new LocalDate("2013-03-31"))) shouldBe "SpecialRate"
    }


    "return correct pool for new range3 car with <=95 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 95, dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "FYA"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 95, dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "FYA"
    }
    "return correct pool for new range3 car with 96-130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 96, dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 96, dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 130, dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 130, dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "MainRate"
    }
    "return correct pool for new range3 car with >130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 131, dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "SpecialRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 131, dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "SpecialRate"
    }

    "return correct pool for 2nd hand range3 car with <=130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = false, price = 10, emissions = 130, dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = false, price = 10, emissions = 130, dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "MainRate"
    }
    "return correct pool for 2nd hand range3 car with >130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = false, price = 10, emissions = 131, dateOfPurchase = new LocalDate("2013-04-01"))) shouldBe "SpecialRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = false, price = 10, emissions = 131, dateOfPurchase = new LocalDate("2015-03-31"))) shouldBe "SpecialRate"
    }


    "return correct pool for new range4 car with <=75 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 75, dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "FYA"
    }
    "return correct pool for new range4 car with 76-130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 76, dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "MainRate"
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 130, dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "MainRate"
    }
    "return correct pool for new range4 car with >130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 131, dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "SpecialRate"
    }
    "return correct pool for 2nd had range4 car with <=130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = false, price = 10, emissions = 130, dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "MainRate"
    }
    "return correct pool for 2nd hand range4 car with >130 emissions" in {
      calculator.taxPoolForCar(Car(regNumber = "XYZ123A", isNew = false, price = 10, emissions = 131, dateOfPurchase = new LocalDate("2015-04-01"))) shouldBe "SpecialRate"
    }


    "return x for fya eligible cars" in {
      val lec01 = LEC01(List(
        Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 1, dateOfPurchase = new LocalDate("2014-01-31")),
        Car(regNumber = "XYZ123A", isNew = true, price = 20, emissions = 666, dateOfPurchase = new LocalDate("2014-01-31")),
        Car(regNumber = "XYZ123A", isNew = true, price = 40, emissions = 1, dateOfPurchase = new LocalDate("2014-01-31"))
      ))
      calculator.getFYAPoolSum(lec01) shouldBe 50
    }
    "return x for main rate pool cars" in {
      val lec01 = LEC01(List(
        Car(regNumber = "XYZ123A", isNew = true, price = 10, emissions = 129, dateOfPurchase = new LocalDate("2014-01-31")),
        Car(regNumber = "XYZ123A", isNew = true, price = 20, emissions = 666, dateOfPurchase = new LocalDate("2014-01-31")),
        Car(regNumber = "XYZ123A", isNew = false, price = 40, emissions = 129, dateOfPurchase = new LocalDate("2014-01-31"))
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
      Car(regNumber = "CAR1", isNew = true, price = 100, emissions = 110, dateOfPurchase = new LocalDate("2009-04-01")),
      Car(regNumber = "CAR2", isNew = false, price = 111, emissions = 150, dateOfPurchase = new LocalDate("2009-04-01")),
      Car(regNumber = "CAR3", isNew = true, price = 222, emissions = 180, dateOfPurchase = new LocalDate("2009-04-01")),
      Car(regNumber = "CAR16", isNew = false, price = 333, emissions = 165, dateOfPurchase = new LocalDate("2009-04-01")),
      Car(regNumber = "CAR4", isNew = true, price = 444, emissions = 115, dateOfPurchase = new LocalDate("2013-03-31")),
      Car(regNumber = "CAR5", isNew = true, price = 555, emissions = 90, dateOfPurchase = new LocalDate("2015-03-31")),
      Car(regNumber = "CAR6", isNew = true, price = 666, emissions = 96, dateOfPurchase = new LocalDate("2015-03-31")),
      Car(regNumber = "CAR7", isNew = false, price = 777, emissions = 130, dateOfPurchase = new LocalDate("2015-03-31")),
      Car(regNumber = "CAR8", isNew = true, price = 888, emissions = 140, dateOfPurchase = new LocalDate("2015-03-31")),
      Car(regNumber = "CAR17", isNew = false, price = 999, emissions = 150, dateOfPurchase = new LocalDate("2015-03-31")),
      Car(regNumber = "CAR9", isNew = true, price = 1111, emissions = 75, dateOfPurchase = new LocalDate("2015-04-01")),
      Car(regNumber = "CAR10", isNew = true, price = 2222, emissions = 76, dateOfPurchase = new LocalDate("2015-06-01")),
      Car(regNumber = "CAR11", isNew = false, price = 3333, emissions = 130, dateOfPurchase = new LocalDate("2015-06-01")),
      Car(regNumber = "CAR12", isNew = true, price = 4444, emissions = 131, dateOfPurchase = new LocalDate("2015-06-01")),
      Car(regNumber = "CAR18", isNew = false, price = 5555, emissions = 131, dateOfPurchase = new LocalDate("2015-06-01")),
      Car(regNumber = "CAR13", isNew = true, price = 6666, emissions = 1, dateOfPurchase = new LocalDate("2008-02-01")),
      Car(regNumber = "CAR14", isNew = false, price = 7777, emissions = 1, dateOfPurchase = new LocalDate("2008-02-01")),
      Car(regNumber = "CAR15", isNew = true, price = 8888, emissions = 600, dateOfPurchase = new LocalDate("2008-02-01")),
      Car(regNumber = "CAR19", isNew = false, price = 9999, emissions = 600, dateOfPurchase = new LocalDate("2008-02-01"))
    ))
    calculator.getFYAPoolSum(lec01) shouldBe 1766
    calculator.getMainRatePoolSum(lec01) shouldBe 40883
    calculator.getSpecialRatePoolSum(lec01) shouldBe 12441
  }


  "calculateSpecialRatePoolBalancingCharge" should {

    "return CP670 of None if CPQ8 is true" in {
      val result = calculator.calculateSpecialRatePoolBalancingCharge(specialRatesCarList,
        cpq8 = CPQ8(Some(true)),
        cp666 = CP666(Some(50)),
        cp667 = CP667(Some(200))
      )

      result shouldBe CP670(None)
    }

    "return CP670 of None if CPQ8 is None" in {
      val result = calculator.calculateSpecialRatePoolBalancingCharge(specialRatesCarList,
        cpq8 = CPQ8(None),
        cp666 = CP666(Some(50)),
        cp667 = CP667(Some(200))
      )

      result shouldBe CP670(None)
    }

    "return CP670 of None if CP667 < CP666 + SpecialRatePool" in {
      val result = calculator.calculateSpecialRatePoolBalancingCharge(specialRatesCarList,
        cpq8 = CPQ8(Some(false)),
        cp666 = CP666(Some(50)),
        cp667 = CP667(Some(100))
      )

      result shouldBe CP670(None)
    }

    "return CP670 of 101 if CP667 > CP666 + SpecialRatePool" in {
      val result = calculator.calculateSpecialRatePoolBalancingCharge(specialRatesCarList,
        cpq8 = CPQ8(Some(false)),
        cp666 = CP666(Some(50)),
        cp667 = CP667(Some(201))
      )

      result shouldBe CP670(Some(101))
    }

    "return CP670 of None if CP667 == CP666 + SpecialRatePool" in {
      val result = calculator.calculateSpecialRatePoolBalancingCharge(specialRatesCarList,
        cpq8 = CPQ8(Some(false)),
        cp666 = CP666(Some(50)),
        cp667 = CP667(Some(100))
      )

      result shouldBe CP670(None)
    }

  }

  "calculateSpecialRatePoolWrittenDownValueCarriedForward" should {

    "return CP669 of 0 if CPQ8 is true and CP666 + SpecialRatePool - CP668 - CP667 is less then zero" in {
      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(specialRatesCarList,
        cpq8 = CPQ8(Some(true)),
        cp666 = CP666(Some(50)),
        cp667 = CP667(Some(100)),
        cp668 = CP668(Some(1000))
      )

      result shouldBe CP669(Some(0))
    }

    "return CP669 of 200 if CPQ8 is true and CP666 + SpecialRatePool - CP668 - CP667 is 200" in {
      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(specialRatesCarList,
        cpq8 = CPQ8(Some(true)),
        cp666 = CP666(Some(250)),
        cp667 = CP667(Some(20)),
        cp668 = CP668(Some(80))
      )

      result shouldBe CP669(Some(200))
    }

    "return CP669 of 200 if CPQ8 is None and CP666 + SpecialRatePool - CP668 - CP667 is 200" in {
      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(specialRatesCarList,
        cpq8 = CPQ8(None),
        cp666 = CP666(Some(250)),
        cp667 = CP667(Some(20)),
        cp668 = CP668(Some(80))
      )

      result shouldBe CP669(Some(200))
    }

    "return CP669 of 0 if CPQ8 is false and CP667 > CP666 + SpecialRatePool" in {
      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(specialRatesCarList,
        cpq8 = CPQ8(Some(false)),
        cp666 = CP666(Some(250)),
        cp667 = CP667(Some(1000)),
        cp668 = CP668(Some(80))
      )

      result shouldBe CP669(Some(0))
    }

    "return CP669 of 200 if CPQ8 is false and CP667 < CP666 + SpecialRatePool" in {
      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(specialRatesCarList,
        cpq8 = CPQ8(Some(false)),
        cp666 = CP666(Some(250)),
        cp667 = CP667(Some(20)),
        cp668 = CP668(Some(80))
      )

      result shouldBe CP669(Some(200))
    }

    "return CP669 of 0 if CPQ8 is false and CP667 = CP666 + SpecialRatePool" in {
      val result = calculator.calculateSpecialRatePoolWrittenDownValueCarriedForward(specialRatesCarList,
        cpq8 = CPQ8(Some(false)),
        cp666 = CP666(Some(250)),
        cp667 = CP667(Some(300)),
        cp668 = CP668(Some(80))
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
