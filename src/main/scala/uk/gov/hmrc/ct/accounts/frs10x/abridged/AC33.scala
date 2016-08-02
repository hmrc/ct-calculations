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

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.ProfitOrLossBeforeTaxCalculator
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC33(value: Option[Int]) extends CtBoxIdentifier(name = "Profit or loss before tax (previous PoA)") with CtOptionalInteger

object AC33 extends Calculated[AC33, AbridgedAccountsBoxRetriever] with ProfitOrLossBeforeTaxCalculator {

  override def calculate(boxRetriever: AbridgedAccountsBoxRetriever): AC33 = {
    import boxRetriever._
    calculateAC33(ac27(), ac29(), ac31())
  }
}
