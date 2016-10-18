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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.ProfitOrLossFinancialYearCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, Frs10xFilingQuestionsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC36(value: Option[Int]) extends CtBoxIdentifier(name = "Profit or loss for financial year (current PoA)")
  with CtOptionalInteger
  with ValidatableBox[Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever with FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever): Set[CtValidation] = {
    failIf(boxRetriever.hmrcFiling().value || boxRetriever.acQ8161().orFalse)(
      boxRetriever match {
        case br: FullAccountsBoxRetriever => validateFull(br)
        case _ => validateAbridged(boxRetriever)
      }
    )
  }

  private def validateFull(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    validateAtLeastOneBoxHasValue(ac12(), ac14(), ac18(), ac20(), ac28(), ac30(), ac34())
  }

  private def validateAbridged(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    validateAtLeastOneBoxHasValue(ac16(), ac18(), ac20(), ac28(), ac30(), ac34())
  }

  private def validateAtLeastOneBoxHasValue(boxes: CtOptionalInteger*): Set[CtValidation] = {
    if (noValue(boxes)) {
      Set(CtValidation(boxId = None, "error.profit.loss.one.box.required"))
    } else {
      Set.empty
    }
  }

  private def noValue(values: Seq[CtOptionalInteger]): Boolean = {
    !values.exists(_.value.nonEmpty)
  }
}

object AC36 extends Calculated[AC36, Frs102AccountsBoxRetriever] with ProfitOrLossFinancialYearCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC36 = {
    import boxRetriever._
    calculateAC36(ac32(), ac34())
  }
}
