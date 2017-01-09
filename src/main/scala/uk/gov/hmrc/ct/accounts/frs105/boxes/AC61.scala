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

import uk.gov.hmrc.ct.accounts.frs105.calculations.NetCurrentAssetsLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC61(value: Option[Int]) extends CtBoxIdentifier(name = "Net current assets or liabilities (previous PoA)") with CtOptionalInteger

object AC61 extends Calculated[AC61, Frs105AccountsBoxRetriever] with NetCurrentAssetsLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever): AC61 = {
    import boxRetriever._
    calculatePreviousNetCurrentAssetsLiabilities(ac456(), ac466(), ac59())
  }
}
