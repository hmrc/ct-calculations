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
import uk.gov.hmrc.ct.computations.formats.Buildings
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class SBA01(buildings: List[Building] = List.empty) extends CtBoxIdentifier(name = "Structures and buildings allowance buildings")
  with CtValue[List[Building]]
  with Input
  with ValidatableBox[ComputationsBoxRetriever] {

  override def value = buildings

  override def asBoxString = Buildings.asBoxString(this)

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    buildings.foldRight(Set[CtValidation]())( (acc, x) =>
    acc.validate(boxRetriever) ++ x
    )
  }
}

case class Building(
                     name: Option[String],
                     postcode: Option[String],
                     earliestWrittenContract: Option[LocalDate],
                     nonResidentialActivityStart: Option[LocalDate],
                     cost: Option[Int],
                     claim: Option[Int]
                   ) extends ValidatableBox[ComputationsBoxRetriever] with ExtraValidation with SBAHelper {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {

    val endOfAccountingPeriod: LocalDate = boxRetriever.cp2().value

      collectErrors(
        validateAsMandatory(nameId, name),
        validateStringMaxLength(nameId, name.getOrElse(""), 100),
        postCodeValidation(postcodeId, postcode),
        dateValidation(endOfAccountingPeriod),
        validateAsMandatory(costId, cost)
    )
  }

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
}
