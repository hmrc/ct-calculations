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

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.{ProfitOrLossBeforeTaxCalculator, ProfitOrLossFinancialYearCalculator}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}

case class AC36(value: Int) extends CtBoxIdentifier(name = "Profit or loss for financial year (current PoA)") with CtInteger

object AC36 extends Calculated[AC36, Frs10xAccountsBoxRetriever] with ProfitOrLossFinancialYearCalculator {

  override def calculate(boxRetriever: Frs10xAccountsBoxRetriever): AC36 = {
    import boxRetriever._
    calculateAC36(retrieveAC32(), retrieveAC34())
  }
}
