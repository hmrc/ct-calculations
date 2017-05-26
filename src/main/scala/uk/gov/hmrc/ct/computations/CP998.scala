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
import uk.gov.hmrc.ct.computations.calculations.LossesSetAgainstOtherProfitsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP998(value: Option[Int]) extends CtBoxIdentifier(name = "Losses this AP set against other profits this AP") with CtOptionalInteger

object CP998 extends Calculated[CP998, ComputationsBoxRetriever] with LossesSetAgainstOtherProfitsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP998 = {
    calculateLossesSetAgainstProfits(cato01 = fieldValueRetriever.cato01(),
                                     cp997 = fieldValueRetriever.cp997(),
                                     cp118 = fieldValueRetriever.cp118(),
                                     cpq19 = fieldValueRetriever.cpQ19())
  }

}
