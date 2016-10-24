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

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC132(value: Option[Int]) extends CtBoxIdentifier(name = "Net book value of tangible assets at the end of the previous period")
  with CtOptionalInteger with BalanceSheetTangibleAssetsCalculator
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators{

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    failIf (boxRetriever.ac44.hasValue) (
      collectErrors(
        validateNetBookValueMatchesTotalAssets(boxRetriever)
      )
    )
  }

  def validateNetBookValueMatchesTotalAssets(boxRetriever: Frs102AccountsBoxRetriever)() = {
    failIf(this.orZero != boxRetriever.ac44().orZero) {
      Set(CtValidation(None, "error.tangible.assets.note.currentNetBookValue.notEqualToAssets"))
    }
  }
}

object AC132 extends Calculated[AC132, Frs102AccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC132 = {
    calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(
      boxRetriever.ac217(),
      boxRetriever.ac131()
    )
  }
}
