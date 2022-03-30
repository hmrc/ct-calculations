/*
 * Copyright 2022 HM Revenue & Customs
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
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, SelfValidatableBox, _}

case class AC7998(value: Option[Int]) extends CtBoxIdentifier(name = "Employee information note") with CtOptionalInteger with Input with SelfValidatableBox[Frs105AccountsBoxRetriever, Option[Int]] {

  private val minNumberOfEmployees = 0
  private val maxNumberOfEmployees = 99999
  private val mandatoryNotesStartDate: LocalDate = new LocalDate(2017,1,1)

  override def validate(boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {

    val startOfAccountingPeriod: LocalDate = boxRetriever.ac3().value

    passIf(startOfAccountingPeriod.isBefore(mandatoryNotesStartDate)) {
      collectErrors(
        validateIntegerRange(minNumberOfEmployees, maxNumberOfEmployees),
        validateIntegerAsMandatory()
      )
    }
  }
}
