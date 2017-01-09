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

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalFixedAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC49(value: Option[Int]) extends CtBoxIdentifier(name = "Total fixed assets (previous PoA)") with CtOptionalInteger

object AC49 extends Calculated[AC49, Frs102AccountsBoxRetriever] with TotalFixedAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC49 = {
    import boxRetriever._
    calculatePreviousTotalFixedAssets(ac43(), ac45())
  }
}
