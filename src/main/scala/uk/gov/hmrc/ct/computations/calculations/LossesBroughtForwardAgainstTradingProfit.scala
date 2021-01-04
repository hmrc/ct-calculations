/*
 * Copyright 2021 HM Revenue & Customs
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

trait LossesBroughtForwardAgainstTradingProfitCalculator extends CtTypeConverters {

  def lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17: CPQ17,
                                                              cp281: CP281,
                                                              cp282: CP282,
                                                              cp283a: CP283a,
                                                              cp283b: CP283b): CP283 = {
    val result = if (cp283a.hasValue || cp283b.hasValue) {
      Some(cp283a + cp283b)
    }
    else {
      for {
        cpq17 <- cpq17.value
        cp282 <- cp282.value if cpq17
      } yield cp282 min cp281.orZero
    }
    CP283(result)
  }

  def lossesBroughtForwardUsedAgainstTradingProfitForProfitsChargeable(cp283: CP283): CP290 = {
    CP290(cp283.value.filter(_ > 0))
  }
}
