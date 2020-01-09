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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs105.validation.OffBalanceSheetArrangementsValidator
import uk.gov.hmrc.ct.box.ValidatableBox.{StandardCohoTextFieldLimit, ValidNonForeignMoreRestrictiveCharacters}
import uk.gov.hmrc.ct.box._

case class AC7999(value: Option[String])
    extends CtBoxIdentifier(name = "Off balance sheet disclosure footnote")
    with CtOptionalString
    with Input
    with SelfValidatableBox[Frs105AccountsBoxRetriever, Option[String]]
    with OffBalanceSheetArrangementsValidator
{
  override def validate(
      boxRetriever: Frs105AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateAgainstAC7999a(boxRetriever, this.boxId, value),
      validateOptionalStringByLength(1, StandardCohoTextFieldLimit)
    )
  }
}
