/*
 * Copyright 2020 HM Revenue & Customs
 *
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
      nameValidation(firstLineOfAddressId, firstLineOfAddress),
      postCodeValidation(postcodeId, postcode),
      dateValidation(endOfAccountingPeriod),
      validateAsMandatory(costId, cost),
      claimAmountValidation(startOfAccountingPeriod, endOfAccountingPeriod, buildingIndex),
      broughtForwardValidation(buildingIndex),
      carriedForwardValidation(buildingIndex),
      validateOptionalStringByLength(claimNote, 1, StandardCohoTextFieldLimit, "SBA01K", Some(s"building$buildingIndex."))
    )
  }

  private def nameValidation(boxId: String, name: Option[String]) =
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

  private def claimAmountValidation(apStart: LocalDate, epEnd: LocalDate, buildingIndex: Int): Set[CtValidation] = {
    claim match {
      case Some(claimAmount) => {
        if (claimAmount < 1) {
          Set(CtValidation(Some(s"building$buildingIndex.SBA01I"), "error.SBA01I.lessThanOne", None))
        } else if (claimAmount > apportionedTwoPercent(apStart, epEnd)) {
          Set(CtValidation(Some(s"building$buildingIndex.SBA01I"), "error.SBA01I.greaterThanMax", None))

        } else {
          Set.empty
        }
      }
      case None => Set(CtValidation(Some(s"building$buildingIndex.SBA01I"), "error.SBA01I.required", None))
    }
  }

  private def broughtForwardValidation(buildingIndex: Int): Set[CtValidation] = {
    broughtForward match {
      case Some(broughtForwardAmount) => {
        if (broughtForwardAmount < 0) {
          Set(CtValidation(Some(s"building$buildingIndex.SBA01H"), "error.SBA01H.lessthanZero", None))
        } else if (broughtForwardAmount > cost.getOrElse(0)) {
          Set(CtValidation(Some(s"building$buildingIndex.SBA01H"), "error.SBA01H.greaterThanMax", None))
        } else {
          Set.empty
        }
      }
      case None => Set(CtValidation(Some(s"building$buildingIndex.SBA01H"), "error.SBA01H.required", None))
    }
  }

  private def carriedForwardValidation(buildingIndex: Int): Set[CtValidation] = {
    carriedForward match {
      case Some(carriedForwardAmount) => {
        if (carriedForwardAmount < 0) {
          Set(CtValidation(Some(s"building$buildingIndex.SBA01J"), "error.SBA01J.lessthanZero", None))
        } else if (carriedForwardAmount > cost.getOrElse(0)) {
          Set(CtValidation(Some(s"building$buildingIndex.SBA01J"), "error.SBA01J.greaterThanMax", None))
        } else {
          Set.empty
        }
      }
      case None => Set(CtValidation(Some(s"building$buildingIndex.SBA01J"), "error.SBA01J.required", None))
    }
  }
}
