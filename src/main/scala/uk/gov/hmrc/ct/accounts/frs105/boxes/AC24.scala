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

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, Input, ValidatableBox}
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class AC24(value: Option[Int]) extends CtBoxIdentifier(name = "Income from covid-19 business support grants")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with TurnoverValidation {

  val accountsStart = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  override def validate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    collectErrors(
      validateZeroOrPositiveInteger(this),
      failIf(boxRetriever.hmrcFiling().value && !boxRetriever.abridgedFiling().value)(
        collectErrors(
          validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, secondaryIncome = boxRetriever.ac12.orZero)
        )
      )
    )
  }
}
