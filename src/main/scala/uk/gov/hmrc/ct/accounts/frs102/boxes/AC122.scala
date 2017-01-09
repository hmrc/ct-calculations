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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.IntangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.BoxRetriever._


case class AC122(value: Option[Int]) extends CtBoxIdentifier(name = "Net book value at [POA END]")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._

    failIf (anyHaveValue(ac42(), ac43()))(
      collectErrors(
        validateMoney(value, min = 0),
        validateNetBookValueMatchesTotalAssets(boxRetriever)
      )
    )
  }

  def validateNetBookValueMatchesTotalAssets(boxRetriever: Frs102AccountsBoxRetriever)() = {
    failIf(this.orZero != boxRetriever.ac42().orZero) {
      Set(CtValidation(None, "error.intangible.assets.note.currentNetBookValue.notEqualToAssets"))
    }
  }
}

object AC122 extends Calculated[AC122, Frs102AccountsBoxRetriever]
  with IntangibleAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC122 = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever => calculateAbridgedAC122(x.ac117(), x.ac121())
      case x: FullAccountsBoxRetriever => calculateFullAC122(x.ac122A(), x.ac122B())
    }
  }

}
