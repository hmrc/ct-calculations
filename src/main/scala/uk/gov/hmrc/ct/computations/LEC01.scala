/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.formats.Cars
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class LEC01(cars: List[Car] = List.empty) extends CtBoxIdentifier(name = "Low emission car")
  with CtValue[List[Car]]
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def value = cars

  override def asBoxString = Cars.asBoxString(this)

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    (boxRetriever.cpQ1000(), value) match {
      case (CPQ1000(Some(false)) | CPQ1000(None), list) if list.nonEmpty => Set(CtValidation(Some("LEC01"), "error.LEC01.cannot.exist"))
      case (CPQ1000(Some(true)), list) if list.isEmpty => Set(CtValidation(Some("LEC01"), "error.LEC01.required"))
      case _ => Set.empty
    }
  }
}

case class Car(regNumber: String,
               isNew: Boolean,
               price: Int,
               emissions: Int,
               dateOfPurchase: LocalDate
               ) extends ValidatableBox[ComputationsBoxRetriever]
  with ExtraValidation  {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val startOfAccountingPeriod = boxRetriever.cp1()
    val endOfAccountingPeriod = boxRetriever.cp2()

    collectErrors(
//      validateRegNumber,
//      validatePriceOfCar,
      validateDateOfPurchase(startOfAccountingPeriod, endOfAccountingPeriod)
    )
  }



  private def validateDateOfPurchase(startOfAccountingPeriod: CP1, endOfAccountingPeriod: CP2): Set[CtValidation] = {
    if (startOfAccountingPeriod.value.isBefore(dateOfPurchase) || endOfAccountingPeriod.value.isAfter(dateOfPurchase)) {
      Set(CtValidation(Some("LEC01B"), "block.lowEmissionCar.dateOfPurchase.outOfRange"))
    } else
      Set.empty
  }


//  private val validateRegNumber: Set[CtValidation] = {
//      validateAsMandatory("LEC01A", regNumber) // this probably needs a max length
//  }
//
//  private val validatePriceOfCar: Set[CtValidation] = {
//    validateIntegerInRange("LEC01B", price, 1, )
//  }


//  var result = !isNaN(date.getTime()) && date.getTime() >= sd.getTime() && date.getTime() <= ed.getTime();



  //@apStartDate = @{
  //    filingState.filing.hmrc.map( hmrcData =>
  //        DateTimeFormat.forPattern("yyyy-MM-dd")
  //        .print(hmrcData.accountPeriodDetails.accountingPeriod.startDate) + "T00:00:00.000Z"
  //    )
  //}
  //
  //@apEndDate = @{
  //    filingState.filing.hmrc.map(hmrcData =>
  //        DateTimeFormat.forPattern("yyyy-MM-dd")
  //        .print(hmrcData.accountPeriodDetails.accountingPeriod.endDate) + "T00:00:00.000Z"
  //    )
  //}
}
