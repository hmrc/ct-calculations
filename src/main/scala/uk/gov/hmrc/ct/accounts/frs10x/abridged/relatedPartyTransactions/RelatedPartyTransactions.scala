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

import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


case class RelatedPartyTransactions(transactions: List[RelatedPartyTransaction] = List.empty, ac7806: AC7806) extends CtBoxIdentifier(name = "Related party transactions")
  with CtValue[RelatedPartyTransactions]
  with Input
  with ValidatableBox[AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def value = this

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    transactions.flatMap(_.validate(boxRetriever)).toSet
  }
}

case class RelatedPartyTransaction(uuid: String,
                                   ac7801: AC7801,
                                   ac7802: AC7802,
                                   ac7803: AC7803,
                                   ac7804: AC7804,
                                   ac7805: AC7805
//                                      ac7801: Option[AC7801],
//                                      ac7802: Option[AC7802],
//                                      ac7803: Option[AC7803],
//                                      ac7804: Option[AC7804],
//                                      ac7805: Option[AC7805]
                                         ) extends CtBoxIdentifier(name = "Related party transactions")
  with ValidatableBox[AbridgedAccountsBoxRetriever]
  with Input
  with CtValue[RelatedPartyTransaction]
 {

  override def value = this

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever): Set[CtValidation] =
    collectErrors(
      () => ac7801.validate(boxRetriever),
      () => ac7802.validate(boxRetriever),
      () => ac7803.validate(boxRetriever),
      () => ac7804.validate(boxRetriever),
      () => ac7805.validate(boxRetriever)

//        () => ac7801.map(box => box.validate(boxRetriever)).getOrElse(Set()),
//  () => ac7802.map(box => box.validate(boxRetriever)).getOrElse(Set()),
//  () => ac7803.map(box => box.validate(boxRetriever)).getOrElse(Set()),
//  () => ac7804.map(box => box.validate(boxRetriever)).getOrElse(Set()),
//  () => ac7805.map(box => box.validate(boxRetriever)).getOrElse(Set())
    )
}





