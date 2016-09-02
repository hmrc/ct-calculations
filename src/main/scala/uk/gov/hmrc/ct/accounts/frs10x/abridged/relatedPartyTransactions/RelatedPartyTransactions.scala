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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.relatedPartyTransactions

import uk.gov.hmrc.ct.accounts.frs10x.abridged.formats.RelatedPartyTransactionsFormatter
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class RelatedPartyTransactions(transactions: List[RelatedPartyTransaction] = List.empty) extends CtBoxIdentifier(name = "Related party transactions")
  with CtValue[List[RelatedPartyTransaction]]
  with Input
  with ValidatableBox[Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def value = transactions

 // override def asBoxString = RelatedPartyTransactionsFormatter.asBoxString(this)

  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = Set()
}

case class RelatedPartyTransaction(txId: String,
                                   ac7801: AC7801,
                                   ac7802: AC7802,
                                   ac7803: AC7803,
                                   ac7804: AC7804,
                                   ac7805: AC7805
                                         ) extends CtBoxIdentifier(name = "Related party transactions")
  with ValidatableBox[Frs10xDirectorsBoxRetriever]
  with CtValue[RelatedPartyTransaction] {

  override def value = this

  override def validate(boxRetriever: Frs10xDirectorsBoxRetriever): Set[CtValidation] = Set()
}




