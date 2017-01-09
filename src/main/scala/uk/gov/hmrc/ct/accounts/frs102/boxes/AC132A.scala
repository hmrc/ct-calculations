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

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box._

case class AC132A(value: Option[Int]) extends CtBoxIdentifier(name = "Tangible assets - Fixtures and fittings - net book value - at POA END")
  with CtOptionalInteger
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value)
    )
  }

}

object AC132A extends Calculated[AC132A, FullAccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC132A = {
    import boxRetriever._
    calculateAC132A(
      ac127A(),
      ac131A()
    )
  }
}
