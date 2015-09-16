/*
 * Copyright 2015 HM Revenue & Customs
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
    writtenDownValue(cpq8 = boxRetriever.retrieveCPQ8(),
                     cpq10 = boxRetriever.retrieveCPQ10(),
                     cp78 = boxRetriever.retrieveCP78(),
                     cp81 = boxRetriever.retrieveCP81(),
                     cp82 = boxRetriever.retrieveCP82(),
                     cp84 = boxRetriever.retrieveCP84(),
                     cp186 = boxRetriever.retrieveCP186(),
                     cp91 = boxRetriever.retrieveCP91())
  }
}
