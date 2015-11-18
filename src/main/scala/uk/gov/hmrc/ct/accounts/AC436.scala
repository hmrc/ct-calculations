/*
 * Copyright 2015 HM Revenue & Customs
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

import uk.gov.hmrc.ct.accounts.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC436(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Profit or loss") with CtOptionalInteger

object AC436 extends Calculated[AC436, AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC436 = {
    calculatePreviousProfitOrLoss(ac13 = boxRetriever.retrieveAC13(), ac406 = boxRetriever.retrieveAC406(),
                                  ac411 = boxRetriever.retrieveAC411(), ac416 = boxRetriever.retrieveAC416(),
                                  ac421 = boxRetriever.retrieveAC421(), ac426 = boxRetriever.retrieveAC426(),
                                  ac35 = boxRetriever.retrieveAC35(), boxRetriever.retrieveMicroEntityFiling())
  }
}
