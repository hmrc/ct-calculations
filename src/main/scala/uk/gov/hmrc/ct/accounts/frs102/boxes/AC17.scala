/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.AccountsPreviousPeriodValidation
import uk.gov.hmrc.ct.accounts.frs102.calculations.GrossProfitAndLossCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC17(value: Option[Int]) extends CtBoxIdentifier(name = "Gross profit or loss (previous PoA)")
  with CtOptionalInteger
  with Input
  with SelfValidatableBox[Frs102AccountsBoxRetriever, Option[Int]]
  with AccountsPreviousPeriodValidation
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      validateInputAllowed("AC17", boxRetriever.ac205()),
      validateMoney(value)
    )
  }
}

object AC17 extends Calculated[AC17, FullAccountsBoxRetriever] with GrossProfitAndLossCalculator {
  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC17 = {
    calculateAC17(boxRetriever.ac13(), boxRetriever.ac15())
  }
}
