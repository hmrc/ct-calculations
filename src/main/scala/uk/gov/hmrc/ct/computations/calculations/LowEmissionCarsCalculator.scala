package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.RoundingFunctions._

import scala.math.BigDecimal.RoundingMode


trait LowEmissionCarsCalculator extends CtTypeConverters {

  val firstYearAllowance = "FYA"
  val mainRate = "MainRate"
  val specialRate = "SpecialRate"

  def taxPoolForCar(car: Car): String = {
    car.dateOfPurchase match {
      case (d) if d.isBefore(new LocalDate("2009-04-01")) => range1(car)
      case (d) if d.isBefore(new LocalDate("2013-04-01")) => range2(car)
      case (d) if d.isBefore(new LocalDate("2015-04-01")) => range3(car)
      case _ => range4(car)

    }
  }

  private def range1(car: Car): String = mainRate

  private def range2(car: Car): String = {
    (car.isNew, car.emissions) match {
      case (true, em) if em <= 110 => firstYearAllowance
      case (true, em) if em > 110 && em <= 160 => mainRate
      case (false, em) if em <= 160 => mainRate
      case (_, em) if em > 160 => specialRate
    }
  }

  private def range3(car: Car): String = {
    (car.isNew, car.emissions) match {
      case (true, em) if em <= 95 => firstYearAllowance
      case (true, em) if em > 95 && em <= 130 => mainRate
      case (false, em) if em <= 130 => mainRate
      case (_, em) if em > 130 => specialRate
    }
  }

  private def range4(car: Car): String = {
    (car.isNew, car.emissions) match {
      case (true, em) if em <= 75 => firstYearAllowance
      case (true, em) if em > 75 && em <= 130 => mainRate
      case (false, em) if em <= 130 => mainRate
      case (_, em) if em > 130 => specialRate

    }
  }

  def getFYAPoolSum(lec01: LEC01): Int = getSomePoolSum(lec01, firstYearAllowance)  //CPaux1

  def getMainRatePoolSum(lec01: LEC01): Int = getSomePoolSum(lec01, mainRate) //CPaux2

  def getSpecialRatePoolSum(lec01: LEC01): Int = getSomePoolSum(lec01, specialRate) //CPaux3

  private def getSomePoolSum(lec01: LEC01, poolString: String): Int = {
    lec01.cars.filter(x => taxPoolForCar(x) == poolString).map(_.price).sum
  }

  //RANGE1
  //Before 1st April 2009	            -	            -	            Main rate

  //RANGE2
  //1st April 2009 - 31st March 2013	New	          <= 110 g/Km   FYA
  //1st April 2009 - 31st March 2013	New	          111-160 g/km	Main rate
  //1st April 2009 - 31st March 2013	Second hand	  <= 160 g/Km 	Main rate
  //1st April 2009 - 31st March 2013	-	            > 160 g/km	  Special rate

  //RANGE3
  //1st April 2013 - 31st March 2015	New	          <= 95 g/km    FYA
  //1st April 2013 - 31st March 2015	New	          96-130 g/km	  Main rate
  //1st April 2013 - 31st March 2015	Second hand	  <=130 g/km	  Main rate
  //1st April 2013 - 31st March 2015	-	            > 130 g/km	  Special rate

  //RANGE4
  //1st April 2015 and thereafter	    New	          <=75 g/km     FYA
  //1st April 2015 and thereafter	    New	          76-130 g/km	  Main rate
  //1st April 2015 and thereafter	    Second hand	  <=130 g/km    Main rate
  //1st April 2015 and thereafter	    -	            >130 g/km	    Special rate

  def calculateSpecialRatePoolBalancingCharge(lec01: LEC01,
                                              cpq8: CPQ8,
                                              cp666: CP666,
                                              cp667: CP667): CP670 = {
    val calc = cpq8.value match {
      case Some(false) if disposalsExceedsSpecialRatePool(lec01,cp666,cp667) => {
        Some(
          cp667 - (cp666 + roundDownToInt(getSpecialRatePoolSum(lec01)))
        )
      }
      case _ => None
    }

    CP670(calc)
  }

  def calculateSpecialRatePoolWrittenDownValueCarriedForward(lec01: LEC01,
                                                             cpq8: CPQ8,
                                                             cp666: CP666,
                                                             cp667: CP667,
                                                             cp668: CP668): CP669 = {
    val calc = cpq8.value match {
      case Some(false) if disposalsExceedsSpecialRatePool(lec01,cp666,cp667) => Some(0)
      case _ => Some(0 max (cp666 + getSpecialRatePoolSum(lec01).toInt - cp668 - cp667))
    }

    CP669(calc)
  }

  def disposalsExceedsSpecialRatePool(lec01: LEC01,
            cp666: CP666,
            cp667: CP667):Boolean =
    cp667 > (cp666 + roundDownToInt(getSpecialRatePoolSum(lec01)))

  def disposalsLessThanSpecialRatePool(lec01: LEC01,
                                      cp666: CP666,
                                      cp667: CP667):Boolean =
    cp667 < (cp666 + roundDownToInt(getSpecialRatePoolSum(lec01)))

  def disposalsExceedsMainRatePool(lec01: LEC01,
                                   cp78: CP78,
                                   cp82: CP82,
                                   cp672: CP672):Boolean = {
    val cp672Value = cp672.value.getOrElse(0)
    cp672Value > cp78 + cp82 + roundDownToInt(getMainRatePoolSum(lec01))
  }

  def disposalsLessThanMainRatePool(lec01: LEC01,
                                    cp78: CP78,
                                    cp82: CP82,
                                    cp672: CP672):Boolean = {
    val cp672Value = cp672.value.getOrElse(0)
    cp672Value < cp78 + cp82 + roundDownToInt(getMainRatePoolSum(lec01))
  }
}


