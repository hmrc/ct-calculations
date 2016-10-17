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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xFilingQuestionsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC16(value: Option[Int]) extends CtBoxIdentifier(name = "Gross profit or loss (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever): Set[CtValidation] = {

    failIf(boxRetriever.hmrcFiling().value || boxRetriever.acQ8161().orFalse)(
      collectErrors(
        validateMoney(value),
        validateAtLeastOne(boxRetriever)
      )
    )
  }

  private def validateAtLeastOne(boxRetriever: AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever)(): Set[CtValidation] = {
    import boxRetriever._
    val anyProfitOrLossFieldHasAValue =
      (value orElse
        ac18().value orElse
        ac20().value orElse
        ac28().value orElse
        ac30().value orElse
        ac34().value)
        .nonEmpty

    passIf(anyProfitOrLossFieldHasAValue)(
      Set(CtValidation(boxId = None, "error.abridged.profit.loss.one.box.required"))
    )
  }
}
