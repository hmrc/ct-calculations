/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x.retriever

import uk.gov.hmrc.ct.accounts.frs10x.boxes._
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs10xDormancyBoxRetriever extends AccountsBoxRetriever {
  self: FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever =>

  def acq8999(): ACQ8999

  def acq8991(): ACQ8991

  def acq8989(): ACQ8989

  def acq8990(): ACQ8990

  def ac8089(): AC8089

  def notTradedStatementRequired(): NotTradedStatementRequired = NotTradedStatementRequired.calculate(this)

  def profitAndLossStatementRequired(): ProfitAndLossStatementRequired = ProfitAndLossStatementRequired.calculate(this)
}
