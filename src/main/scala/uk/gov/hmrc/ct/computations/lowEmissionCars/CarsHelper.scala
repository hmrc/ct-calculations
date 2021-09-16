/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.lowEmissionCars

import uk.gov.hmrc.ct.computations.CP669.taxPoolForCar
import uk.gov.hmrc.ct.computations.calculations.LowEmissionCarRate

object CarsHelper {

  val lec01BoxId = "LEC01"
  val registrationNumberId = "LEC01A"
  val isCarNewId = "LEC01B"
  val priceId = "LEC01C"
  val emissionsId = "LEC01D"
  val dateOfPurchaseId = "LEC01E"

  def assignCarPool(carsFromFilingState: List[AbstractLowEmissionCar]): Map[LowEmissionCarRate, List[AbstractLowEmissionCar]] =
    carsFromFilingState.groupBy( car => taxPoolForCar(car))

  def filterCarsByPool(carsGroupedByPool: Map[LowEmissionCarRate, List[AbstractLowEmissionCar]], rate: LowEmissionCarRate)= {
    carsGroupedByPool.get(rate)
  }
}
