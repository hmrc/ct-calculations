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

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.BalanceSheetTangibleAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC131(value: Int) extends CtBoxIdentifier(name = "Total net assets or liabilities (previous PoA)")
  with CtInteger with BalanceSheetTangibleAssetsCalculator {
}

object AC131 extends Calculated[AC131, AbridgedAccountsBoxRetriever] with BalanceSheetTangibleAssetsCalculator {

  override def calculate(boxRetriever: AbridgedAccountsBoxRetriever): AC131 = {
    calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(
      boxRetriever.ac5131(),
      boxRetriever.ac219(),
      boxRetriever.ac130(),
      boxRetriever.ac214()
    )
  }
}
