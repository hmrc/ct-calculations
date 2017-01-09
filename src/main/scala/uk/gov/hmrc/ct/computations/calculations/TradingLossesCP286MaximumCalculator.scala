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

import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.computations.{CP117, CP281, CP998}

trait TradingLossesCP286MaximumCalculator {

  def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp998: CP998, cp281: CP281): Int = {

    (cp117.value, cato01.value, cp281.value, cp998.value) match {
      case (0, ntp, _, Some(lossesCurrentToCurrent)) => (ntp - lossesCurrentToCurrent) max 0
      case (0, ntp, _, _) => ntp
      case (tp, ntp, None, None) => tp + ntp
      case (tp, ntp, Some(lossesPreviousToCurrent), _) if lossesPreviousToCurrent > tp => ntp
      case (tp, ntp, Some(lossesPreviousToCurrent), _) => tp + ntp - lossesPreviousToCurrent
      case _ => 0
    }
  }
}
