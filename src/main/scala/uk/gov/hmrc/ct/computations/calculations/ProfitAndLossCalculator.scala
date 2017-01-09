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
import uk.gov.hmrc.ct.computations._

trait ProfitAndLossCalculator extends CtTypeConverters {

  def calculateProfitOrLoss(cp7: CP7, cp8: CP8): CP14 = CP14(cp7 minus cp8)

  def calculateGrossProfitOrLossBeforeTax(cp14: CP14, cp40: CP40, cp43: CP43, cp509: CP509, cp502: CP502): CP44 = {
    CP44(cp14 - cp40 + cp502 + cp509 + cp43)
  }

}
