/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.accounts.frs10x.formats.DirectorsFormatter
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.validation.DirectorsReportEnabled
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants._

case class Directors(directors: List[Director] = List.empty) extends CtBoxIdentifier(name = "Directors.")
  with CtValue[List[Director]]
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with DirectorsReportEnabled {

  override def value = directors

  override def asBoxString = DirectorsFormatter.asBoxString(this)

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    validateDirectorRequired(boxRetriever) ++
      validateAtLeastOneDirectorIsAppointedIfAppointmentsIsYes(boxRetriever) ++
      validateAtLeastOneDirectorResignedIfResignationsIsYes(boxRetriever) ++
      validateAtMost12Directors() ++
      validateDirectorsUnique() ++
    directors.foldRight(Set[CtValidation]())((dd, tail) => dd.validate(boxRetriever) ++ tail)
  }

  def validateAtLeastOneDirectorIsAppointedIfAppointmentsIsYes(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    failIf (
      directorsReportEnabled(boxRetriever) &&
      boxRetriever.acQ8003().value.getOrElse(false) &&
      directors.forall(_.ac8005.getOrElse(false) == false)
    ) {
      Set(CtValidation(Some("ac8005"), "error.Directors.ac8005.global.atLeast1", None))
    }
  }

  def validateAtLeastOneDirectorResignedIfResignationsIsYes(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    failIf (
      directorsReportEnabled(boxRetriever) &&
        boxRetriever.acQ8009().value.getOrElse(false) &&
        directors.forall(_.ac8011.getOrElse(false) == false)
    ) {
      Set(CtValidation(Some("ac8011"), "error.Directors.ac8011.global.atLeast1", None))
    }
  }

  def validateDirectorRequired(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
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
                    ac8013: Option[LocalDate] = None  // resignation date
                     ) extends ValidatableBox[Frs10xAccountsBoxRetriever] {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] =
    validateStringByLength("ac8001", ac8001, "Directors.ac8001", 1, 40) ++
      validateStringByRegex("ac8001", ac8001, "Directors.ac8001", validCoHoCharacters) ++
      validateAppointmentDateAsMandatoryWhenAppointed(boxRetriever) ++
      validateAppointmentDateAsWithinPOA(boxRetriever)

  def validateAppointmentDateAsMandatoryWhenAppointed(boxRetriever: Frs10xAccountsBoxRetriever) = {
    (ac8005, ac8007) match {
      case (Some(true), _) => validateDateAsMandatory(s"ac8007.$id", ac8007, "ac8007")
      case _ => Set()
    }
  }
  
  def validateAppointmentDateAsWithinPOA(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
    (ac8007, boxRetriever.ac3().value, boxRetriever.ac4().value) match {
      case (Some(appDate), ac3, ac4) => validateDateAsBetweenInclusive(s"ac8007.$id", ac8007, ac3, ac4, "ac8007")
      case _ => Set()
    }
  }
}



