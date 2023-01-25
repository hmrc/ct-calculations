/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.lowEmissionCars

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.CPQ1000
import uk.gov.hmrc.ct.computations.formats.Cars
import uk.gov.hmrc.ct.computations.lowEmissionCars.CarsHelper._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class LEC01(values: List[Car] = List.empty) extends CtBoxIdentifier(name = "Low emission car")
  with CtValue[List[Car]]
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def value = values

  override def asBoxString = Cars.asBoxString(this)

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val errorsRegardingCPQ1000 = (boxRetriever.cpQ1000(), value) match {
      case (CPQ1000(Some(false)) | CPQ1000(None), list) if list.nonEmpty => Set(CtValidation(Some(lec01BoxId), s"error.$lec01BoxId.cannot.exist"))
      case (CPQ1000(Some(true)), list) if list.isEmpty => Set(CtValidation(Some(lec01BoxId), s"error.$lec01BoxId.required"))
      case _ => validationSuccess
    }
    val carErrors = values.foldLeft(Set[CtValidation]())((errors, car) => car.validate(boxRetriever) ++ errors)

    errorsRegardingCPQ1000 ++ carErrors
  }
}

case class Car(override val regNumber: Option[String],
               override val isNew: Option[Boolean],
               override val price: Option[Int],
               override val emissions: Option[Int],
               override val dateOfPurchase: Option[LocalDate]
               ) extends AbstractLowEmissionCar with ValidatableBox[ComputationsBoxRetriever]
  with ExtraValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val startOfAccountingPeriod = boxRetriever.cp1().value
    val endOfAccountingPeriod = boxRetriever.cp2().value

    collectErrors(
    validateRegNumber,
    validateIsCarNew,
    validatePriceOfCar,
    validateEmissionsOfCar,
    validateDateOfPurchase(startOfAccountingPeriod, endOfAccountingPeriod)
    )
  }

    private val validateRegNumber: Set[CtValidation] = validateAsMandatory(registrationNumberId, regNumber)

    private val validateIsCarNew = validateAsMandatory(isCarNewId, isNew)

    private val validatePriceOfCar: Set[CtValidation] = collectErrors(
      validateAsMandatory(priceId, price),
      validateZeroOrPositiveInteger(priceId, price)
    )

    private val validateEmissionsOfCar = collectErrors(
      validateAsMandatory(emissionsId, emissions),
      validateZeroOrPositiveInteger(emissionsId, emissions)
    )

    private def validateDateOfPurchase(startOfAccountingPeriod: LocalDate, endOfAccountingPeriod: LocalDate): Set[CtValidation] = {
    collectErrors(
      validateAsMandatory(dateOfPurchaseId, dateOfPurchase)(),
      validateDateIsInclusive(dateOfPurchaseId, startOfAccountingPeriod, dateOfPurchase, endOfAccountingPeriod)
    )
  }
}

object Car {
  def apply(abstractCar: AbstractLowEmissionCar) = {
    new Car(
      regNumber = abstractCar.regNumber,
      isNew = abstractCar.isNew,
      price = abstractCar.price,
      emissions = abstractCar.emissions,
      dateOfPurchase = abstractCar.dateOfPurchase
    )
  }
}
