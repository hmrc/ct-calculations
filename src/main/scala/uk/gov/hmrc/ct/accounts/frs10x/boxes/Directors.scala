/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.accounts.frs102.validation.DirectorsReportEnabledCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox.ValidCoHoNamesCharacters
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class Directors(directors: List[Director] = List.empty) extends CtBoxIdentifier(name = "Directors.")
  with CtValue[List[Director]]
  with Input
  with ValidatableBox[Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever]
  with DirectorsReportEnabledCalculator {

  override def value = directors

  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    validateDirectorRequired(boxRetriever) ++
      validateAtLeastOneDirectorIsAppointedIfAppointmentsIsYes(boxRetriever) ++
      validateAtLeastOneDirectorResignedIfResignationsIsYes(boxRetriever) ++
      validateAtMost12Directors() ++
      validateDirectorsUnique() ++
    directors.foldRight(Set[CtValidation]())((dd, tail) => dd.validate(boxRetriever) ++ tail)
  }

  def validateAtLeastOneDirectorIsAppointedIfAppointmentsIsYes(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    failIf (
      directorsReportEnabled(boxRetriever) &&
      boxRetriever.acQ8003().value.getOrElse(false) &&
      directors.forall(_.ac8005.getOrElse(false) == false)
    ) {
      Set(CtValidation(Some("ac8005"), "error.Directors.ac8005.global.atLeast1", None))
    }
  }

  def validateAtLeastOneDirectorResignedIfResignationsIsYes(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    failIf (
      directorsReportEnabled(boxRetriever) &&
        boxRetriever.acQ8009().value.getOrElse(false) &&
        directors.forall(_.ac8011.getOrElse(false) == false)
    ) {
      Set(CtValidation(Some("ac8011"), "error.Directors.ac8011.global.atLeast1", None))
    }
  }

  def validateDirectorRequired(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    failIf (directorsReportEnabled(boxRetriever) && directors.isEmpty) {
      Set(CtValidation(Some("ac8001"), "error.Directors.ac8001.global.atLeast1", None))
    }
  }

  def validateAtMost12Directors(): Set[CtValidation] = {
    directors.size match {
      case n if n > 12 => Set(CtValidation(Some("ac8001"), "error.Directors.ac8001.atMost12", None))
      case _ => Set.empty
    }
  }

  def validateDirectorsUnique(): Set[CtValidation] = {

    val uniqueNames = directors.map(_.ac8001).toSet

    directors.size != uniqueNames.size match {
      case true => Set(CtValidation(Some("ac8001"), "error.Directors.ac8001.unique", None))
      case false => Set.empty
    }
  }


}

case class Director(id: String,
                    ac8001: String,                   // name
                    ac8005: Option[Boolean] = None,   // appointed
                    ac8011: Option[Boolean] = None,   // resigned
                    ac8007: Option[LocalDate] = None, // appointed date
                    ac8013: Option[LocalDate] = None,  // resignation date
                    ac199A: Option[Boolean] = None  // approver
                     ) extends ValidatableBox[Frs10xDirectorsBoxRetriever] {

  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever): Set[CtValidation] =
    validateStringByLength("ac8001", ac8001, "Directors.ac8001", 1, 40) ++
      validateRawStringByRegex("ac8001", ac8001, errorCodeBoxId = "Directors.ac8001", ValidCoHoNamesCharacters) ++
      validateAppointmentDateAsMandatoryWhenAppointed(boxRetriever) ++
      validateAppointmentDateAsWithinPOA(boxRetriever) ++
      validateResignationDateAsMandatoryWhenResigned(boxRetriever) ++
      validateResignationDateAsWithinPOA(boxRetriever) ++
      validateAppointments(boxRetriever) ++
      validateResignations(boxRetriever)

  def validateAppointmentDateAsMandatoryWhenAppointed(boxRetriever: Frs10xDirectorsBoxRetriever) = {
    (ac8005, ac8007) match {
      case (Some(true), _) => validateDateAsMandatory(s"ac8007.$id", ac8007, "ac8007")
      case _ => Set()
    }
  }

  def validateAppointmentDateAsWithinPOA(boxRetriever: Frs10xDirectorsBoxRetriever): Set[CtValidation] = {
    (ac8007, boxRetriever.ac3().value, boxRetriever.ac4().value) match {
      case (Some(appDate), ac3, ac4) => validateDateAsBetweenInclusive(s"ac8007.$id", ac8007, ac3, ac4, "ac8007")
      case _ => Set()
    }
  }

  def validateResignationDateAsMandatoryWhenResigned(boxRetriever: Frs10xDirectorsBoxRetriever) = {
    (ac8011, ac8013) match {
      case (Some(true), _) => validateDateAsMandatory(s"ac8013.$id", ac8013, "ac8013")
      case _ => Set()
    }
  }

  def validateResignationDateAsWithinPOA(boxRetriever: Frs10xDirectorsBoxRetriever): Set[CtValidation] = {
    (ac8013, boxRetriever.ac3().value, boxRetriever.ac4().value) match {
      case (Some(appDate), ac3, ac4) => validateDateAsBetweenInclusive(s"ac8013.$id", ac8013, ac3, ac4, "ac8013")
      case _ => Set()
    }
  }

  def validateAppointments(boxRetriever: Frs10xDirectorsBoxRetriever) = {
    if (boxRetriever.acQ8003().value.contains(false)) {
      val ac8005Errors = if (ac8005.nonEmpty) Set(CtValidation(Some("AC8005"), s"error.director.$id.box.AC8005.cannot.exist")) else Set.empty
      val ac8007Errors = if (ac8007.nonEmpty) Set(CtValidation(Some("AC8007"), s"error.director.$id.box.AC8007.cannot.exist")) else Set.empty

      ac8005Errors ++ ac8007Errors
    } else
      Set.empty
  }

  def validateResignations(boxRetriever: Frs10xDirectorsBoxRetriever) = {
    if (boxRetriever.acQ8009().value.contains(false)) {
      val ac8011Errors = if (ac8011.nonEmpty) Set(CtValidation(Some("AC8011"), s"error.director.$id.box.AC8011.cannot.exist")) else Set.empty
      val ac8013Errors = if (ac8013.nonEmpty) Set(CtValidation(Some("AC8013"), s"error.director.$id.box.AC8013.cannot.exist")) else Set.empty

      ac8011Errors ++ ac8013Errors
    } else
      Set.empty
  }
}
