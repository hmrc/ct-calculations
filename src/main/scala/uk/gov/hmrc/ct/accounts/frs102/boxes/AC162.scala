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

import uk.gov.hmrc.ct.accounts.frs102.calculations.BalanceSheetCreditorsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC162(value: Option[Int]) extends CtBoxIdentifier(name = "Creditors after one year - Total (CY)")
  with CtOptionalInteger
  with ValidatableBox[FullAccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value),
      failIf(boxRetriever.ac64().hasValue)(totalEqualToCurrentAmount(boxRetriever))
    )
  }

  def totalEqualToCurrentAmount(boxRetriever: FullAccountsBoxRetriever)() = {
    failIf(this.orZero != boxRetriever.ac64().orZero) {
      Set(CtValidation(None, "error.creditorsAfterOneYear.currentYearTotal.notEqualsTo.currentYearAmount"))
    }
  }

}

object AC162 extends Calculated[AC162, FullAccountsBoxRetriever] with BalanceSheetCreditorsCalculator {

  override def calculate(boxRetriever: FullAccountsBoxRetriever): AC162 = {
    import boxRetriever._
    calculateAC162(
      ac156(),
      ac158(),
      ac160()
    )
  }

}
