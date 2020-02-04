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

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.formats.Buildings
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class SBA01(buildings: List[Building] = List.empty) extends CtBoxIdentifier(name = "Structures and buildings allowance buildings")
  with CtValue[List[Building]]
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def value = buildings

  override def asBoxString = Buildings.asBoxString(this)

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    buildings.zipWithIndex.flatMap(b => {
      val (building, buildingIndex) = b
      building.claim match {
        case Some(claimAmount) => {
          if (claimAmount < 0) {
            Some(CtValidation (Some(s"SBA01F.building$buildingIndex"), "Claim amount cannot be below zero", None) )
          } else if(claimAmount > building.apportionedTwoPercent(boxRetriever.cp1().value, boxRetriever.cp2().value)) {
            Some(CtValidation (Some(s"SBA01F.building$buildingIndex"), "Claim amount cannot be greater than apportioned 2%", None) )

          } else {
            None
          }
        }
        case None => None
      }
    }).toSet
  }
}

case class Building(
                     name: Option[String],
                     postcode: Option[String],
                     earliestWrittenContract: Option[LocalDate],
                     nonResidentialActivityStart: Option[LocalDate],
                     cost: Option[Int],
                     claim: Option[Int]
                   ) extends SBACalculator {
  def apportionedTwoPercent(apStartDate: LocalDate, apEndDate: LocalDate) = getAmountClaimableForSBA(apStartDate, apEndDate, nonResidentialActivityStart, cost).getOrElse(0)
}
