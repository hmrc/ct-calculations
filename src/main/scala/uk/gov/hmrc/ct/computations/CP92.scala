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

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP92(value: Option[Int]) extends CtBoxIdentifier(name = "Written down value brought forward") with CtOptionalInteger

object CP92 extends Calculated[CP92, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(boxRetriever: ComputationsBoxRetriever): CP92 = {
    writtenDownValue(cpq8 = boxRetriever.cpQ8(),
                     cp78 = boxRetriever.cp78(),
                     cp82 = boxRetriever.cp82(),
                     cp89 = boxRetriever.cp89(),
                     cp91 = boxRetriever.cp91(),
                     cp672 = boxRetriever.cp672(),
                     cato20 = boxRetriever.cato20(),
                     cpAux2 = boxRetriever.cpAux2())
  }
}
