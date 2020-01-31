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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.retriever.{ComputationsBoxRetriever, ComputationsBuildingsBoxRetriever}

case class CP296(value: Option[Int]) extends CtBoxIdentifier(name = "Total Structure and Building Allowance") with CtOptionalInteger

object CP296 extends Calculated[CP296, ComputationsBoxRetriever] with SBACalculator {


  def getAllowanceForEachBuilding(boxRetriever: ComputationsBoxRetriever): List[Option[Int]] = {
    boxRetriever.sba01().buildings.map(
      building => getAmountClaimableForSBA(
        boxRetriever.cp1().value,
        boxRetriever.cp2().value,
        building.nonResidentialActivityStart,
        building.cost
      )
    )
  }


  override def calculate(boxRetriever: ComputationsBoxRetriever): CP296 = {

    CP296(sumAmount(getAllowanceForEachBuilding(boxRetriever)))
  }
}