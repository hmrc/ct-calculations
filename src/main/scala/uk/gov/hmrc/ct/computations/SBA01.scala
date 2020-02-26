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
import uk.gov.hmrc.ct.box.ValidatableBox.StandardCohoTextFieldLimit
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

    buildings.foldRight(Set[CtValidation]())( (building, errors) =>
    building.validate(boxRetriever) ++ errors
    )
  }
}

case class Building(
                     description: Option[String],
                     firstLineOfAddress: Option[String],
                     postcode: Option[String],
                     earliestWrittenContract: Option[LocalDate],
                     nonResidentialActivityStart: Option[LocalDate],
                     costsClaimedInThisPeriod: Option[Boolean],
                     cost: Option[Int],
                     claim: Option[Int],
                     broughtForward: Option[Int] = None,
                     carriedForward: Option[Int] = None,
                     claimNote: Option[String] = None
                   ) extends ValidatableBox[ComputationsBoxRetriever] with ExtraValidation with SBAHelper with SBACalculator {
  def apportionedTwoPercent(apStartDate: LocalDate, apEndDate: LocalDate) = getAmountClaimableForSBA(apStartDate, apEndDate, nonResidentialActivityStart, cost).getOrElse(0)

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {

    val endOfAccountingPeriod: LocalDate = boxRetriever.cp2().value
    val startOfAccountingPeriod: LocalDate = boxRetriever.cp1().value
    val buildingIndex: Int = boxRetriever.sba01().buildings.indexOf(this)

    collectErrors(
      mandatoryTextValidation(firstLineOfAddressId, firstLineOfAddress),
      mandatoryTextValidation(descriptionId, description),
      postCodeValidation(postcodeId, postcode),
      dateValidation(endOfAccountingPeriod),
      totalCostValidation(costId, cost),
      validateAsMandatory(filingPeriodQuestionId, costsClaimedInThisPeriod),
      claimAmountValidation(claimId, startOfAccountingPeriod, endOfAccountingPeriod, buildingIndex),
      broughtForwardValidation(broughtForwardId, buildingIndex),
      carriedForwardValidation(carriedForwardId, buildingIndex),
      validateOptionalStringByLength(claimNote, 1, StandardCohoTextFieldLimit, claimNoteId, Some(s"building$buildingIndex."))
    )
  }

  private def mandatoryTextValidation(boxId: String, name: Option[String]) =
    validateAsMandatory(boxId, name) ++ validateStringMaxLength(boxId, name.getOrElse(""), 100)

  private def postCodeValidation(boxId: String, postcode: Option[String]): Set[CtValidation] =
      validateAsMandatory(boxId, postcode) ++ validatePostcode(boxId, postcode)

  private def dateValidation(dateUpperBound: LocalDate): Set[CtValidation] =
    earliestWrittenContractValidation(dateUpperBound) ++ nonResidentialActivityValidation(dateUpperBound)

  private def earliestWrittenContractValidation(dateUpperBound: LocalDate): Set[CtValidation] =
    collectErrors(
      validateAsMandatory(earliestWrittenContractId, earliestWrittenContract),
      validateDateIsInclusive(earliestWrittenContractId, dateLowerBound, earliestWrittenContract, dateUpperBound)
    )

  private def nonResidentialActivityValidation(dateUpperBound: LocalDate): Set[CtValidation] = {
    collectErrors(
      validateAsMandatory(nonResActivityId, nonResidentialActivityStart),
      validateDateIsInclusive(nonResActivityId, dateLowerBound, nonResidentialActivityStart, dateUpperBound)
    )
  }

  private def totalCostValidation(boxId: String, totalCost: Option[Int]): Set[CtValidation] = {
    totalCost match {
      case Some(cost) if(cost < 1) => Set(CtValidation(Some(boxId), s"error.$boxId.lessThanOne", None))
      case Some(cost) if(cost > 99999999) => Set(CtValidation(Some(boxId), s"error.$boxId.moreThanMax", None))
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => Set.empty
    }
  }

  private def claimAmountValidation(boxId: String, apStart: LocalDate, epEnd: LocalDate, buildingIndex: Int): Set[CtValidation] = {
    claim match {
      case Some(claimAmount) => {
        if (claimAmount < 1) {
          Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.lessThanOne", None))
        } else if (claimAmount > apportionedTwoPercent(apStart, epEnd)) {
          Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.greaterThanMax", None))

        } else {
          Set.empty
        }
      }
      case None => Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.required", None))
    }
  }

  private def broughtForwardValidation(boxId: String, buildingIndex: Int): Set[CtValidation] = {
    broughtForward match {
      case Some(broughtForwardAmount) => {
        if (broughtForwardAmount < 0) {
          Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.lessthanZero", None))
        } else if (broughtForwardAmount > cost.getOrElse(0)) {
          Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.greaterThanMax", None))
        } else {
          Set.empty
        }
      }
      case None => Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.required", None))
    }
  }

  private def carriedForwardValidation(boxId: String, buildingIndex: Int): Set[CtValidation] = {
    carriedForward match {
      case Some(carriedForwardAmount) => {
        if (carriedForwardAmount < 0) {
          Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.lessthanZero", None))
        } else if (carriedForwardAmount > cost.getOrElse(0)) {
          Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.greaterThanMax", None))
        } else {
          Set.empty
        }
      }
      case None => Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.required", None))
    }
  }
}
