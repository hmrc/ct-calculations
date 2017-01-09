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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.AdjustedTradingProfitForPeriodCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP282(value: Option[Int]) extends CtBoxIdentifier(name = "Adjusted Trading Profit for the Period") with CtOptionalInteger

object CP282 extends Calculated[CP282, ComputationsBoxRetriever] with AdjustedTradingProfitForPeriodCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP282 = {
    adjustedTradingProfitForPeriodCalculation(cp117 = fieldValueRetriever.cp117(),
                                              cpq17 = fieldValueRetriever.cpQ17())
  }
}
