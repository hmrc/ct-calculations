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

case class CP186(value: Option[Int]) extends CtBoxIdentifier(name = "Total Allowances") with CtOptionalInteger

object CP186 extends Calculated[CP186, ComputationsBoxRetriever] with MachineryAndPlantCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP186 = {
    computeTotalAllowancesClaimed(cpq8 = fieldValueRetriever.cpQ8(),
                                  cp87 = fieldValueRetriever.cp87(),
                                  cp88 = fieldValueRetriever.cp88(),
                                  cp89 = fieldValueRetriever.cp89(),
                                  cp90 = fieldValueRetriever.cp90())
  }

}
