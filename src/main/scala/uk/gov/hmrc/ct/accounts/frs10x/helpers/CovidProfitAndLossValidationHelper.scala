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

package uk.gov.hmrc.ct.accounts.frs10x.helpers

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC3, AC4}
import uk.gov.hmrc.ct.box.ValidatableBox.OptionalIntIdBox
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.validation.TurnoverValidation

trait CovidProfitAndLossValidationHelper[T <: AccountsBoxRetriever] extends ValidatableBox[T with FilingAttributesBoxValueRetriever] with TurnoverValidation {
  self: OptionalIntIdBox =>

  val value: Option[Int]

  val ac12Id: String = "AC12"
  val ac16Id: String = "AC16"

  val accountsStart: AccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: AccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  val turnover: T => Box

  val grossProfitOrLoss: T => Box

  def validateBox(boxRetriever: BoxRetriever): PartialFunction[Box, Set[CtValidation]]

  def validateTurnoverOrGrossProfitOrLoss(boxRetriever: BoxRetriever): Set[CtValidation]  = {
    if (value.getOrElse(0) <= 0) {
      validationSuccess
    } else {
      validateBox(boxRetriever)(getCorrectBox(boxRetriever))
  }
  }

  def getCorrectBox(boxRetriever: BoxRetriever): Box = {
     val isAbridgedJourney = boxRetriever.abridgedFiling().value
    val correctBoxWithBoxId =
      if (isAbridgedJourney) grossProfitOrLoss(boxRetriever)
      else turnover(boxRetriever)

    correctBoxWithBoxId
  }


   def shortenedValidateHmrcTurnover(boxRetriever: BoxRetriever, box: Box, boxId: String, minimumAmount: Option[Int] = None): Set[CtValidation] = {
     validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, errorSuffix = s".hmrc.turnover.$boxId", secondaryIncome = box.orZero, minimumAmount)
   }

  def shortenedValidateCohoTurnover(boxRetriever: BoxRetriever, box: Box, boxId: String): Set[CtValidation] = {
      validateCoHoTurnover(boxRetriever, accountsStart, accountEnd, secondaryIncome = box.orZero, errorSuffix = s".coho.turnover.$boxId")
    }

  type BoxRetriever = T with FilingAttributesBoxValueRetriever

  type Frs10xBoxRetriever = Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever

  type Box = CtBoxIdentifier with CtOptionalInteger
}
