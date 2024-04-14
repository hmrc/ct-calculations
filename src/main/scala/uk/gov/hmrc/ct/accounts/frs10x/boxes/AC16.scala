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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.{AC3, AC4}
import uk.gov.hmrc.ct.accounts.frs102.calculations.GrossProfitAndLossCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.utils.CatoInputBounds
import uk.gov.hmrc.ct.validation.TurnoverValidation

case class AC16(value: Option[Int]) extends CtBoxIdentifier(name = "Gross profit or loss (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever]
  with Validators with TurnoverValidation {

  val accountsStart: Frs10xAccountsBoxRetriever => AC3 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac3()
  }

  val accountEnd: Frs10xAccountsBoxRetriever => AC4 = {
    boxRetriever: AccountsBoxRetriever =>
      boxRetriever.ac4()
  }

  private val isHmrcFiling: FilingAttributesBoxValueRetriever => Boolean = boxRetriever =>
    boxRetriever.hmrcFiling().value

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever ): Set[CtValidation] = {

    collectErrors(
      requiredErrorIf(boxRetriever.abridgedFiling().value && !boxRetriever.cato24().value.getOrElse(false) && boxRetriever.ac16().value.isEmpty),
      failIf(isHmrcFiling(boxRetriever))(
      collectErrors(
        validateHmrcTurnover(boxRetriever, accountsStart, accountEnd, minimumAmount = Some(CatoInputBounds.oldMinValue99999999))
      )
    ),
    failIf(!isHmrcFiling(boxRetriever) && boxRetriever.companiesHouseFiling().value)(
      collectErrors(
        validateCoHoTurnover(boxRetriever, accountsStart, accountEnd)
      )
    )
    )
  }
}
object AC16 extends Calculated[AC16, Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever] with GrossProfitAndLossCalculator {
  override def calculate(boxRetriever: Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever ): AC16 = {
    if(!boxRetriever.cato24().isTrue && boxRetriever.abridgedFiling().value) {
      AC16(None)
    } else {
      calculateAC16(boxRetriever.ac12, boxRetriever.ac24, boxRetriever.ac401, boxRetriever.ac403, boxRetriever.ac14())
    }
  }
}