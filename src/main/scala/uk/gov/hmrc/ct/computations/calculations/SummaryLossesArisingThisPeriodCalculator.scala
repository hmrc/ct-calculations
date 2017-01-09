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

import uk.gov.hmrc.ct.computations.{CP118, CP235}
import uk.gov.hmrc.ct.ct600.v2.B122

trait SummaryLossesArisingThisPeriodCalculator {

  def summaryLossesArisingThisPeriodCalculation(cp118: CP118): CP235 = {
    CP235(
      cp118.value match {
        case t if t > 0 => Some(t)
        case _ => None
      })
  }

  def summaryTradingLossesArisingCalculation(cp118: CP118): B122 = {
    B122(
      cp118.value match {
      case t if t > 0 => Some(t)
      case _ => Some(0)
    })
  }
}
