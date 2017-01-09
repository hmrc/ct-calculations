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

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP117, CP283, CP284, CP291}

trait NetTradingProfitCalculator extends CtTypeConverters {

  def netTradingProfitCalculation(cp117: CP117,
                                  cp283: CP283): CP284 = {
    CP284(Some(cp117.value - cp283.orZero))
  }

  def netTradingProfitForProfitsChargeable(netTradingProfit: CP284, lossesBroughtForwardUsedAgainstTradingProfit: CP283): CP291 = {
    val result = lossesBroughtForwardUsedAgainstTradingProfit.value match {
      case Some(x) if x > 0 => netTradingProfit.value
      case _ => None
    }
    CP291(result)
  }
}
