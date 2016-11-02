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

package uk.gov.hmrc.ct.accounts.frs105.retriever

import uk.gov.hmrc.ct.accounts.frs102.abridged.accountsApproval.{CompaniesHouseAccountsApproval, HmrcAccountsApproval}
import uk.gov.hmrc.ct.accounts.frs102.abridged.loansToDirectors.LoansToDirectors
import uk.gov.hmrc.ct.accounts.frs102.abridged.relatedPartyTransactions.RelatedPartyTransactions
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.accounts.frs105.boxes.AC405
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

trait Frs105AccountsBoxRetriever extends AccountsBoxRetriever {

  self: FilingAttributesBoxValueRetriever =>

  def ac405(): AC405

 }
