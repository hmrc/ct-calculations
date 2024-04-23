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

package uk.gov.hmrc.ct.computations.machineryAndPlant

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP105(value: Int) extends CtBoxIdentifier(name = "Total expenditure qualifying for main rate") with CtInteger

object CP105 extends Calculated[CP105, ComputationsBoxRetriever] with MachineryAndPlantCalculationsLogic {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP105 = {
    val carsQualifyingForMainRate = boxRetriever.cpAux2().value
    val mainRateExpenditureOtherThanCars = boxRetriever.cp82().value.getOrElse(0)
    val writtenDownValueBroughtForward=boxRetriever.cp78().value.getOrElse(0)
    val fyaNotClaimed= boxRetriever.cp97().value
    val aiaNotClaimed = boxRetriever.cp110().value
    val total = sumOf(
      carsQualifyingForMainRate,
      mainRateExpenditureOtherThanCars,
      writtenDownValueBroughtForward,
      fyaNotClaimed,
      aiaNotClaimed
    )

    CP105(total)
  }
}

