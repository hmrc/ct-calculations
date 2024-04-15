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

package uk.gov.hmrc.ct.computations

import java.time.LocalDate
import uk.gov.hmrc.ct.box.ValidatableBox.StandardCohoTextFieldLimit
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.calculations.SBACalculator
import uk.gov.hmrc.ct.computations.formats.Buildings
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants.toErrorArgsFormat

case class SBA01(values: List[Building] = List.empty) extends CtBoxIdentifier(name = "Structures and buildings allowance buildings")
  with CtValue[List[Building]]
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def value = values

  override def asBoxString = Buildings.asBoxString(this)

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    values.foldRight(Set[CtValidation]())((building, errors) =>
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
  def getApportionedRates(apStartDate: LocalDate, apEndDate: LocalDate): Int = getSbaDetails(apStartDate, apEndDate, nonResidentialActivityStart, cost).flatMap(_.totalCost).getOrElse(0)

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {

    val endOfAccountingPeriod: LocalDate = boxRetriever.cp2().value
    val startOfAccountingPeriod: LocalDate = boxRetriever.cp1().value
    val buildingIndex: Int = boxRetriever.sba01().values.indexOf(this)

    collectErrors(
      mandatoryTextValidation(firstLineOfAddressId, firstLineOfAddress),
      mandatoryTextValidation(descriptionId, description),
      validatePostcode(postcodeId, postcode),
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

  private def dateValidation(dateUpperBound: LocalDate): Set[CtValidation] =
    earliestWrittenContractValidation(dateUpperBound) ++ nonResidentialActivityValidation(dateUpperBound)

  private def earliestWrittenContractValidation(dateUpperBound: LocalDate): Set[CtValidation] =
    collectErrors(
      validateAsMandatory(earliestWrittenContractId, earliestWrittenContract),
      validateDateIsInclusive(earliestWrittenContractId, dateLowerBound, earliestWrittenContract, dateUpperBound)
    )

  private def nonResidentialActivityValidation(dateUpperBound: LocalDate): Set[CtValidation] = {
    val betweenErrorMessage = s"error.$nonResActivityId.not.betweenInclusive"
    val (earliestDate: LocalDate, errorMessage: String) = earliestWrittenContract.map(date => {
      if(dateLowerBound.isBefore(date)) (date, s"error.$nonResActivityId.not.greaterThanEarliestContract") else (dateLowerBound, betweenErrorMessage)
    }).getOrElse((dateLowerBound, betweenErrorMessage))

    nonResidentialActivityStart match {
      case Some(nonResStartDateAmount) => {
        if (earliestDate.isAfter(nonResStartDateAmount)) {
          Set(CtValidation(Some(nonResActivityId), errorMessage, Some(Seq(toErrorArgsFormat(earliestDate), toErrorArgsFormat(dateUpperBound)))))
        } else if (dateUpperBound.isBefore(nonResStartDateAmount)) {
          Set(CtValidation(Some(nonResActivityId), betweenErrorMessage, Some(Seq(toErrorArgsFormat(earliestDate), toErrorArgsFormat(dateUpperBound)))))
        } else {
          Set.empty
        }
      }
      case None => Set(CtValidation(Some(nonResActivityId), s"error.$nonResActivityId.required", None))
    }
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
        } else if (claimAmount > getApportionedRates(apStart, epEnd)) {
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
          Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.lessThanZero", None))
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
        val correctAmountWithMessage: Option[(Int, String)] = for {
          broughtForwardAmount <- broughtForward
          claimAmount <- claim
          costAmount <- cost
        } yield {
          if(broughtForwardAmount == 0) (costAmount - claimAmount, "greaterThanCost")
          else (broughtForwardAmount - claimAmount, "greaterThanBrought")
        }

        if (carriedForwardAmount < 0) {
          Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.lessThanZero", None))
        } else if (correctAmountWithMessage.nonEmpty && carriedForwardAmount != correctAmountWithMessage.get._1) {
          Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.${correctAmountWithMessage.get._2}", None))
        } else {
          Set.empty
        }
      }
      case None => Set(CtValidation(Some(s"building$buildingIndex.$boxId"), s"error.$boxId.required", None))
    }
  }
}

