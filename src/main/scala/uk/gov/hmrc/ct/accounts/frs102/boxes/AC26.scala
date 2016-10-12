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

import uk.gov.hmrc.ct.accounts.frs102.abridged.calculations.OperatingProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frs102.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC26(value: Option[Int]) extends CtBoxIdentifier(name = "Operating profit or loss (current PoA)") with CtOptionalInteger

object AC26 extends Calculated[AC26, AbridgedAccountsBoxRetriever] with OperatingProfitOrLossCalculator {

  override def calculate(boxRetriever: AbridgedAccountsBoxRetriever): AC26 = {
    import boxRetriever._
    calculateAC26(ac16(), ac18(), ac20())
  }
}
