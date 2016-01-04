/*
 * Copyright 2016 HM Revenue & Customs
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

case class CP91(value: Option[Int]) extends CtBoxIdentifier(name = "Balancing charge") with CtOptionalInteger

object CP91 extends Calculated[CP91, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP91 = {
    computeBalancingCharge(cpq8 = fieldValueRetriever.retrieveCPQ8(),
                           cp78 = fieldValueRetriever.retrieveCP78(),
                           cp82 = fieldValueRetriever.retrieveCP82(),
                           cp84 = fieldValueRetriever.retrieveCP84(),
                           cp666 = fieldValueRetriever.retrieveCP666(),
                           cp667 = fieldValueRetriever.retrieveCP667(),
                           cp672 = fieldValueRetriever.retrieveCP672(),
                           cp673 = fieldValueRetriever.retrieveCP673(),
                           cp674 = fieldValueRetriever.retrieveCP674(),
                           cpAux1 = fieldValueRetriever.retrieveCPAux1(),
                           cpAux2 = fieldValueRetriever.retrieveCPAux2(),
                           cpAux3 = fieldValueRetriever.retrieveCPAux3(),
                           cato20 = fieldValueRetriever.retrieveCATO20()
    )
  }
}
