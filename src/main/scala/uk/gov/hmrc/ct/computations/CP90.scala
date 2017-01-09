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
import uk.gov.hmrc.ct.computations.calculations.MachineryAndPlantCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP90(value: Option[Int]) extends CtBoxIdentifier(name = "Balance Allowance") with CtOptionalInteger

object CP90 extends Calculated[CP90, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(retriever: ComputationsBoxRetriever): CP90 =
    computeBalanceAllowance(
      retriever.cpQ8(),
      retriever.cp78(),
      retriever.cp84(),
      retriever.cp666(),
      retriever.cp673(),
      retriever.cp674(),
      retriever.cpAux1(),
      retriever.cpAux2(),
      retriever.cpAux3()
    )
}
