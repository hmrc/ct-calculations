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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import java.time.LocalDate
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC7999a(value: Option[Boolean]) extends CtBoxIdentifier(name = "Company does have off balance sheet arrangements")
  with CtOptionalBoolean
  with Input
  with SelfValidatableBox[Frs105AccountsBoxRetriever, Option[Boolean]] {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {

    val startOfAccountingPeriod: LocalDate = boxRetriever.ac3().value
    val mandatoryNotesStartDate: LocalDate = LocalDate.of(2017,1,1)

    passIf(startOfAccountingPeriod.isBefore(mandatoryNotesStartDate)) {
      collectErrors(
        failIf(value.isEmpty) {
          validateAsMandatory()
        }
      )
    }
  }
}
