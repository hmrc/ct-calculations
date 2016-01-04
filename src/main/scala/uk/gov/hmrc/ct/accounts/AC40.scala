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

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC40(value: Option[Int]) extends CtBoxIdentifier(name = "Current Net balance transferred to reserves") with CtOptionalInteger

object AC40 extends Calculated[AC40, AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC40 = {
    calculateCurrentNetBalance(boxRetriever.retrieveAC36(), boxRetriever.retrieveAC38())
  }
}
