/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.RoundingFunctions._
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.computations.lowEmissionCars.{AbstractLowEmissionCar, LEC01}
import uk.gov.hmrc.ct.utils.DateImplicits._

trait LowEmissionCarsCalculator extends CtTypeConverters {
  def taxPoolForCar(car: AbstractLowEmissionCar): LowEmissionCarRate = {
    car.dateOfPurchase match {
      case Some(dateOfPurchase) if dateOfPurchase < new LocalDate("2009-04-01") => range1(car)
      case Some(dateOfPurchase) if dateOfPurchase < new LocalDate("2013-04-01") => range2(car)
      case Some(dateOfPurchase) if dateOfPurchase < new LocalDate("2015-04-01") => range3(car)
      case Some(dateOfPurchase) if dateOfPurchase < new LocalDate("2018-04-01") => range4(car)
      case Some(dateOfPurchase) if dateOfPurchase < new LocalDate("2021-04-01") => range5(car)
      case Some(dateOfPurchase) if dateOfPurchase < new LocalDate("2025-04-01") => range6(car)
      case Some(dateOfPurchase) if dateOfPurchase >= new LocalDate("2025-04-01") => range7(car)
      case _ => ErrorState
    }
  }

  private def range1(car: AbstractLowEmissionCar): LowEmissionCarRate = MainRate

  private def range2(car: AbstractLowEmissionCar): LowEmissionCarRate = {
    (car.isNew, car.emissions) match {
      case (Some(true), Some(em)) if em <= 110 => FYA
      case (Some(true), Some(em)) if em > 110 && em <= 160 => MainRate
      case (Some(false), Some(em)) if em <= 160 => MainRate
      case (Some(_), Some(em)) if em > 160 => SpecialRate
      case _ => ErrorState
    }
  }

  private def range3(car: AbstractLowEmissionCar): LowEmissionCarRate = {
    (car.isNew, car.emissions) match {
      case (Some(true), Some(em)) if em <= 95 => FYA
      case (Some(true), Some(em)) if em > 95 && em <= 130 => MainRate
      case (Some(false), Some(em)) if em <= 130 => MainRate
      case (Some(_), Some(em)) if em > 130 => SpecialRate
      case _ => ErrorState

    }
  }

  private def range4(car: AbstractLowEmissionCar): LowEmissionCarRate = {
      (car.isNew, car.emissions) match {
        case (Some(true), Some(em)) if em <= 75 => FYA
        case (Some(true), Some(em)) if em > 75 && em <= 130 => MainRate
        case (Some(false), Some(em)) if em <= 130 => MainRate
        case (Some(_), Some(em)) if em > 130 => SpecialRate
        case _ => ErrorState
      }
  }

  private def range5(car: AbstractLowEmissionCar): LowEmissionCarRate = {
    (car.isNew, car.emissions) match {
      case (Some(true), Some(em)) if em <= 50 => FYA
        case (Some(true), Some(em)) if em > 50 && em <= 110 => MainRate
        case (Some(false), Some(em)) if em <= 110 => MainRate
        case (Some(_), Some(em)) if em > 110 => SpecialRate
        case _ => ErrorState
    }
  }

  private def range6(car: AbstractLowEmissionCar): LowEmissionCarRate = {
    (car.isNew, car.emissions) match {
      case (Some(true), Some(0)) => FYA
      case (Some(_), Some(em)) if em <= 50 => MainRate
      case (Some(_), Some(em)) if em > 50 => SpecialRate
      case _ => ErrorState
    }
  }

  private def range7(car: AbstractLowEmissionCar): LowEmissionCarRate = {
    (car.isNew, car.emissions) match {
      case (Some(_), Some(em)) if em <= 50 => MainRate
      case (Some(_), Some(em)) if em > 50 => SpecialRate
      case _ => ErrorState
    }
  }

  def getFYAPoolSum(lec01: LEC01): Int = getSomePoolSum(lec01, FYA)  //CPaux1

  def getMainRatePoolSum(lec01: LEC01): Int = getSomePoolSum(lec01, MainRate) //CPaux2

  def getSpecialRatePoolSum(lec01: LEC01): Int = getSomePoolSum(lec01, SpecialRate) //CPaux3

  private def getSomePoolSum(lec01: LEC01, poolGroup: LowEmissionCarRate): Int = {
    lec01.cars.filter(x => taxPoolForCar(x) == poolGroup).map(car =>
    if (car.price.isDefined) car.price.get
    else 0
    ).sum
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
  //1st April 2015 and 31st March 2018	    New	          <=75 g/km     FYA
  //1st April 2015 and 31st March 2018	    New	          76-130 g/km	  Main rate
  //1st April 2015 and 31st March 2018	    Second hand	  <=130 g/km    Main rate
  //1st April 2015 and 31st March 2018	    -	            >130 g/km	    Special rate

  //RANGE5
  //1st April 2018 and thereafter	    New	          <=50 g/km     FYA
  //1st April 2018 and thereafter	    New	          50-110 g/km	  Main rate
  //1st April 2018 and thereafter	    Second hand	  <=110 g/km    Main rate
  //1st April 2018 and thereafter	    -	            >110 g/km	    Special rate

  //RANGE6
  //1st April 2021 and thereafter	    New	                0    g/km   FYA
  //1st April 2021 and thereafter	    New/Second hand	    <=50 g/km	  Main rate
  //1st April 2021 and thereafter	    New/Second hand	    > 50 g/km   Special rate

  //RANGE7
  //1st April 2025 and thereafter	    New/Second hand	    <=50 g/km	  Main rate
  //1st April 2025 and thereafter	    New/Second hand	    > 50 g/km   Special rate

  def calculateSpecialRatePoolBalancingCharge(cpq8: CPQ8,
                                              cp666: CP666,
                                              cp667: CP667,
                                              cpAux3: CPAux3): CP670 = {
    val x = cp666 + cpAux3

    val result = cpq8.value match {
      case Some(false) if cp667 > x => cp667 - x
      case _ => 0
    }

    CP670(Some(result))
  }

  def calculateSpecialRatePoolWrittenDownValueCarriedForward(cpq8: CPQ8,
                                                             cp666: CP666,
                                                             cp667: CP667,
                                                             cp668: CP668,
                                                             cp670: CP670,
                                                             cpAux3: CPAux3): CP669 = {

    val result = (cpq8.value, cp670.value) match {
      case (Some(false), Some(0)) => (cp666 + cpAux3 - cp668 - cp667).max(0)
      case _ => 0
    }

    CP669(Some(result))
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
    val cp672Value = cp672.orZero
    cp672Value > cp78 + cp82 + roundDownToInt(getMainRatePoolSum(lec01))
  }

  def disposalsLessThanMainRatePool(lec01: LEC01,
                                    cp78: CP78,
                                    cp82: CP82,
                                    cp672: CP672):Boolean = {
    val cp672Value = cp672.orZero
    cp672Value < cp78 + cp82 + roundDownToInt(getMainRatePoolSum(lec01))
  }
}

/**
 * Needed to include an ErrorState here because not having a case in the LowEmissionCarsCalculator for unmatched cases
 * was leading to exceptions if the user didn't input any values for emissions or the cars emissions in the edit or add
 * car page.
 */

sealed trait LowEmissionCarRate
  case object FYA extends LowEmissionCarRate
  case object MainRate extends LowEmissionCarRate
  case object SpecialRate extends LowEmissionCarRate
  case object ErrorState extends LowEmissionCarRate
