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

import uk.gov.hmrc.ct.accounts.frs102.calculations.ProfitOrLossBeforeTaxCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC32(value: Option[Int]) extends CtBoxIdentifier(name = "Profit or loss before tax (current PoA)") with CtOptionalInteger

object AC32 extends Calculated[AC32, Frs102AccountsBoxRetriever] with ProfitOrLossBeforeTaxCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC32 = {
    import boxRetriever._
    calculateAC32(ac26(), ac28(), ac30())
  }
}
