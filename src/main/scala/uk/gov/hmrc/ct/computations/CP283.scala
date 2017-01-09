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
import uk.gov.hmrc.ct.computations.calculations.LossesBroughtForwardAgainstTradingProfitCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP283(value: Option[Int]) extends CtBoxIdentifier(name = "Losses brought forward used against trading profit") with CtOptionalInteger

object CP283 extends Calculated[CP283, ComputationsBoxRetriever] with LossesBroughtForwardAgainstTradingProfitCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP283 = {
    lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17 = fieldValueRetriever.cpQ17(),
                                                            cp281 = fieldValueRetriever.cp281(),
                                                            cp282 = fieldValueRetriever.cp282())
  }

}
