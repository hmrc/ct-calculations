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
