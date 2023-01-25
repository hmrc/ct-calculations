/*
 * Copyright 2023 HM Revenue & Customs
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

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalCreditorsWithinOneYearCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.accounts.validation.AssetsEqualToSharesValidator
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._

case class AC155(value: Option[Int]) extends CtBoxIdentifier(name = "Total creditors within one year (previous PoA)")
  with CtOptionalInteger with AssetsEqualToSharesValidator with ValidatableBox[Frs102AccountsBoxRetriever]  {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    failIf (anyHaveValue(ac58(), ac59()))(
      validateMatchesBalanceSheetValue(boxRetriever)
    )
  }

  def validateMatchesBalanceSheetValue(boxRetriever: Frs102AccountsBoxRetriever)() = {
    failIf(value != boxRetriever.ac59().value) {
      Set(CtValidation(None, "error.creditors.within.one.year.note.previous.total.not.equal.balance.sheet"))
    }
  }
}

object AC155 extends Calculated[AC155, FullAccountsBoxRetriever] with TotalCreditorsWithinOneYearCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC155 = {
    import boxRetriever._
    calculatePreviousTotalCreditorsWithinOneYear(ac143(), ac145(), ac147(), ac149(), ac151(), ac153())
  }
}
