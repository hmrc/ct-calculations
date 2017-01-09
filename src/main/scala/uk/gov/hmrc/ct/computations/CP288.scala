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
import uk.gov.hmrc.ct.computations.calculations.LossesCarriedForwardsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP288(value: Option[Int]) extends CtBoxIdentifier(name = "Losses Carried forward") with CtOptionalInteger

object CP288 extends Calculated[CP288, ComputationsBoxRetriever] with LossesCarriedForwardsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP288 = {
    lossesCarriedForwardsCalculation(cpq17 = fieldValueRetriever.cpQ17(),
                                     cpq19 = fieldValueRetriever.cpQ19(),
                                     cpq20 = fieldValueRetriever.cpQ20(),
                                     cp281 = fieldValueRetriever.cp281(),
                                     cp118 = fieldValueRetriever.cp118(),
                                     cp283 = fieldValueRetriever.cp283(),
                                     cp998 = fieldValueRetriever.cp998(),
                                     cp287 = fieldValueRetriever.cp287(),
                                     cato01 = fieldValueRetriever.cato01())
  }

}
