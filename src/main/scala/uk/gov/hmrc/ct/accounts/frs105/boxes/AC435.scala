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

import uk.gov.hmrc.ct.accounts.frs105.calculations.ProfitOrLossFinancialYearCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC435(value: Option[Int]) extends CtBoxIdentifier(name = "Current Profit or loss") with CtOptionalInteger

object AC435 extends Calculated[AC435, Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossFinancialYearCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC435 = {
    import boxRetriever._
    calculateAC435(ac12, ac405, ac410, ac415, ac420, ac425, ac34)
  }
}
