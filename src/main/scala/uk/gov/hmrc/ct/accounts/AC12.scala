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

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.utils.CatoInputBounds.minimumValue0
import uk.gov.hmrc.ct.validation.TurnoverValidation


case class AC12(value: Option[Int]) extends CtBoxIdentifier(name = "Current Turnover/Sales")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with TurnoverValidation {

  val accountsStart: AccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: AccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  override def validate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    val isOpwEnabled = boxRetriever.cato24().value.getOrElse(false)
    val isAbridged = boxRetriever.abridgedFiling().value

    if(!isOpwEnabled && isAbridged) {
      Set.empty
    } else {
        val errors = collectErrors(
          failIf(isFrs10xHmrcAbridgedReturnWithLongPoA(accountsStart, accountEnd)(boxRetriever)) {
            validateAsMandatory(this)
          },
          failIf(boxRetriever.hmrcFiling().value)(
              collectErrors(
              validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, minimumAmount = Some(minimumValue0))
            )
          ),
          failIf(!boxRetriever.hmrcFiling().value && boxRetriever.companiesHouseFiling().value)(
            collectErrors(
              validateCoHoTurnover(boxRetriever, accountsStart, accountEnd, minimumAmount = Some(minimumValue0))
            )
          )
        )

      if(errors.isEmpty) {
        validateMoney(value)
      } else {
        errors
      }
    }
  }
}

object AC12 {
  def apply(value: Int): AC12 = AC12(Some(value))
}
