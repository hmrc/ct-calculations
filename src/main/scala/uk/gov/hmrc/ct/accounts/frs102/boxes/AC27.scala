/*
 * Copyright 2023 HM Revenue & Customs
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

import uk.gov.hmrc.ct.accounts.frs102.calculations.OperatingProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC27(value: Option[Int]) extends CtBoxIdentifier(name = "Operating profit or loss (current PoA)") with CtOptionalInteger

object AC27 extends Calculated[AC27, Frs102AccountsBoxRetriever] with OperatingProfitOrLossCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC27 = {
    boxRetriever match {
      case br: FullAccountsBoxRetriever => calculateAC27(br.ac17(), br.ac19(), br.ac21(), ac23 = br.ac23())
      case br: AbridgedAccountsBoxRetriever => calculateAC27(br.ac17(), br.ac19(), br.ac21(), br.ac25())
    }
  }
}
