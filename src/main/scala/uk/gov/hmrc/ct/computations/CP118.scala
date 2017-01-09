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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, MustBeZeroOrPositive}
import uk.gov.hmrc.ct.computations.calculations.AdjustedTradingProfitOrLossCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class
CP118(value: Int) extends CtBoxIdentifier(name = "Adjusted Trading Loss") with CtInteger with MustBeZeroOrPositive

object CP118 extends Calculated[CP118, ComputationsBoxRetriever] with AdjustedTradingProfitOrLossCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP118 = {
    calculateAdjustedTradingLoss(cp44 = fieldValueRetriever.cp44(),
                                 cp54 = fieldValueRetriever.cp54(),
                                 cp59 = fieldValueRetriever.cp59(),
                                 cp186 = fieldValueRetriever.cp186(),
                                 cp91 = fieldValueRetriever.cp91(),
                                 cp670 = fieldValueRetriever.cp670(),
                                 cp668 = fieldValueRetriever.cp668())
  }

}
