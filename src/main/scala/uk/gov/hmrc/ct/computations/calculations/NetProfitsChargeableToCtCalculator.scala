/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.computations.losses._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

trait NetProfitsChargeableToCtCalculator extends CtTypeConverters {

  def calculateNetProfitsChargeableToCt(totalProfitsBeforeDeductions: CP293,
                                        tradingLossesOfThisPeriodAndLaterPeriods: CP294,
                                        tradingLossesFromEarlierPeriodsUsedAgainstNonTradingProfit: CP997Abstract,
                                        totalDonations: CP999): CP295 = {
    val cp997 = tradingLossesFromEarlierPeriodsUsedAgainstNonTradingProfit.value.getOrElse(0)
    val result = totalProfitsBeforeDeductions - tradingLossesOfThisPeriodAndLaterPeriods - cp997 - totalDonations

    CP295(result max 0)
  }
}
