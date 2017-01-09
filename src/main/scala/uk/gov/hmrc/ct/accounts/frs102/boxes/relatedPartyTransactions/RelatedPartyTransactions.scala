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

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs102.validation.CompoundBoxValidationHelper
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


case class RelatedPartyTransactions(transactions: List[RelatedPartyTransaction] = List.empty, ac7806: AC7806) extends CtBoxIdentifier(name = "Related party transactions")
  with CtValue[RelatedPartyTransactions]
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever] {

  override def value = this

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    collectErrors (
      cannotExistIf(!boxRetriever.ac7800().orFalse && (transactions.nonEmpty || ac7806.value.nonEmpty)),

      failIf(boxRetriever.ac7800().orFalse) {
        collectErrors(
          validateSimpleField(boxRetriever),
          validateTransactionRequired(boxRetriever),
          validateAtMost20transactions(boxRetriever),
          validateTransactions(boxRetriever)
        )
      }
    )
  }
  
  def validateTransactions(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    val transactionsErrorList = for ((transaction, index) <- transactions.zipWithIndex) yield {
      val errors = transaction.validate(boxRetriever)
      errors.map(error => error.copy(
        boxId = Some("RelatedPartyTransactions"),
        errorMessageKey = CompoundBoxValidationHelper.contextualiseErrorKey("transactions", error.errorMessageKey, index)))
    }
    transactionsErrorList.flatten.toSet
  }

  def validateTransactionRequired(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    failIf(transactions.isEmpty) {
      Set(CtValidation(None, "error.RelatedPartyTransactions.atLeast1", None))
    }
  }

  def validateAtMost20transactions(boxRetriever: Frs102AccountsBoxRetriever)(): Set[CtValidation] = {
    failIf(transactions.length > 20) {
      Set(CtValidation(None, "error.RelatedPartyTransactions.atMost20", None))
    }
  }

  def validateSimpleField(boxRetriever: Frs102AccountsBoxRetriever)() = {
    ac7806.validate(boxRetriever).map {
      error => error.copy(boxId = Some("RelatedPartyTransactions"))
    }
  }
}

case class RelatedPartyTransaction(uuid: String,
                                   ac7801: AC7801,
                                   ac299A: AC299A,
                                   ac300A: AC300A,
                                   ac301A: AC301A,
                                   ac302A: AC302A,
                                   ac303A: AC303A
                                         ) extends CtBoxIdentifier(name = "Related party transactions")
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Input
  with CtValue[RelatedPartyTransaction]
 {

  override def value = this

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] =
    collectErrors(
      () => ac7801.validate(boxRetriever),
      () => ac299A.validate(boxRetriever),
      () => ac300A.validate(boxRetriever),
      () => ac301A.validate(boxRetriever),
      () => ac302A.validate(boxRetriever),
      () => ac303A.validate(boxRetriever)
    )
}
