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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC405(value: Option[Int]) extends CtBoxIdentifier(name = "Other income (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDormancyBoxRetriever] {

  override def validate(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDormancyBoxRetriever): Set[CtValidation] = {
    failIf(!boxRetriever.acq8999().orFalse && (boxRetriever.hmrcFiling().value || boxRetriever.acq8161().orFalse)) (
      collectErrors(
        validateMoney(value),
        validateAtLeastOneCurrentYearFieldPopulated(boxRetriever)
      )
    )
  }

  def validateAtLeastOneCurrentYearFieldPopulated(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDormancyBoxRetriever)(): Set[CtValidation] = {

    val anyCurrentYearFieldHasAValue = (
        boxRetriever.ac12().value orElse
        boxRetriever.ac405().value orElse
        boxRetriever.ac410().value orElse
        boxRetriever.ac415().value orElse
        boxRetriever.ac420().value orElse
        boxRetriever.ac425().value orElse
        boxRetriever.ac34().value
      ).nonEmpty
    failIf(!anyCurrentYearFieldHasAValue) {
      Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
    }
  }
}
