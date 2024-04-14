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

import uk.gov.hmrc.ct.accounts.AccountsPreviousPeriodValidation
import uk.gov.hmrc.ct.accounts.frs102.calculations.GrossProfitAndLossCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC17(value: Option[Int]) extends CtBoxIdentifier(name = "Gross profit or loss (previous PoA)")
  with CtOptionalInteger
  with Input
  with SelfValidatableBox[Frs10xAccountsBoxRetriever, Option[Int]]
  with AccountsPreviousPeriodValidation
  with Validators {

  override def validate(boxRetriever: Frs10xAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors (
      validateInputAllowed("AC17", boxRetriever.ac205())
    )
  }
}

object AC17 extends Calculated[AC17, Frs10xAccountsBoxRetriever] with GrossProfitAndLossCalculator {
  override def calculate(boxRetriever: Frs10xAccountsBoxRetriever): AC17 = {
    calculateAC17(boxRetriever.ac13(), boxRetriever.ac25, boxRetriever.ac402, boxRetriever.ac404, boxRetriever.ac15())
  }
}