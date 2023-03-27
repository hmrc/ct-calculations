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

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalCurrentAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC57(value: Option[Int]) extends CtBoxIdentifier(name = "Total current assets (previous PoA)") with CtOptionalInteger

object AC57 extends Calculated[AC57, Frs102AccountsBoxRetriever] with TotalCurrentAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC57 = {
    import boxRetriever._
    calculatePreviousTotalCurrentAssets(ac51(), ac53(), ac55())
  }
}
