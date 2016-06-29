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

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.AC436._
import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC435(value: Option[Int]) extends CtBoxIdentifier(name = "Current Profit or loss") with CtOptionalInteger

object AC435 extends Calculated[AC435, AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC435 = {
    calculateCurrentProfitOrLoss(ac12 = boxRetriever.retrieveAC12(), ac405 = boxRetriever.retrieveAC405(),
                                 ac410 = boxRetriever.retrieveAC410(), ac415 = boxRetriever.retrieveAC415(),
                                 ac420 = boxRetriever.retrieveAC420(), ac425 = boxRetriever.retrieveAC425(),
                                 ac34 = boxRetriever.retrieveAC34(), boxRetriever.retrieveMicroEntityFiling())
  }
}
