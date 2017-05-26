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
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP118, CP997, CP998, CPQ19}

trait LossesSetAgainstOtherProfitsCalculator extends CtTypeConverters {

  def calculateLossesSetAgainstProfits(cato01: CATO01, cp997: CP997, cp118: CP118, cpq19: CPQ19): CP998 = {
    CP998(
      cpq19.value match {
        case Some(true) => Some(cp118.value min (cato01 - cp997))
        case _ => None
      }
    )
  }
}
