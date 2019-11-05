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
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.validation.OffBalanceSheetArrangementsValidator
import uk.gov.hmrc.ct.box.ValidatableBox.StandardCohoTextFieldLimit
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class AC7999(value: Option[String])
    extends CtBoxIdentifier(name = "Off balance sheet disclosure note")
    with CtOptionalString
    with Input
    with SelfValidatableBox[Frs105AccountsBoxRetriever with ComputationsBoxRetriever, Option[String]]
    with OffBalanceSheetArrangementsValidator
{
  override def validate(boxRetriever: Frs105AccountsBoxRetriever with ComputationsBoxRetriever): Set[CtValidation] = {
    val mandatoryNotesStartDate: LocalDate = LocalDate.parse("2017-01-01")
    val accountingPeriodEndDate: LocalDate = boxRetriever.cp2().value

    passIf(accountingPeriodEndDate.isBefore(mandatoryNotesStartDate)) {
      collectErrors(
        validateAgainstAC7999a(boxRetriever, this.boxId, value),
        validateOptionalStringByLength(1, StandardCohoTextFieldLimit)
        //      validateOptionalStringByRegex(boxId, this, ValidNonForeignMoreRestrictiveCharacters) Implement regex here to prevent XSS.
      )
    }
  }
}
