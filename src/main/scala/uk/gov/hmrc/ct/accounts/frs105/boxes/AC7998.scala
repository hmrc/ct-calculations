/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, SelfValidatableBox, _}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class AC7998(value: Option[Int]) extends CtBoxIdentifier(name = "Employee information note") with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {

  private val minNumberOfEmployees = 1
  private val maxNumberOfEmployees = 99999

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
     val mandatoryNotesStartDate: LocalDate = LocalDate.parse("2017-01-01")
     val accountingPeriodEndDate: LocalDate = boxRetriever.cp2().value

    passIf(accountingPeriodEndDate.isBefore(mandatoryNotesStartDate)) {
      collectErrors(
        validateIntegerRange(minNumberOfEmployees, maxNumberOfEmployees),
        validateIntegerAsMandatory()
      )
    }
  }
}
