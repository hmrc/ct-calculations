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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.ProfitAndLossCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP44(value: Int) extends CtBoxIdentifier(name = "Profit or losses before tax") with CtInteger

object CP44 extends Calculated[CP44, ComputationsBoxRetriever] with ProfitAndLossCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP44 = {
    calculateGrossProfitOrLossBeforeTax(cp14 = fieldValueRetriever.cp14(),
                                        cp40 = fieldValueRetriever.cp40(),
                                        cp43 =fieldValueRetriever.cp43(),
                                        cp509 =fieldValueRetriever.cp509(),
                                        cp502 = fieldValueRetriever.cp502())
  }
}
