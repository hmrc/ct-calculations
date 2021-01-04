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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.NetProfitsChargeableToCtCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class CP295(value: Int)
  extends CtBoxIdentifier(name = "Profits chargeable to CT")
    with CtInteger

object CP295 extends Calculated[CP295, ComputationsBoxRetriever] with NetProfitsChargeableToCtCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP295 = {
    calculateNetProfitsChargeableToCt(fieldValueRetriever.cp293(),
      fieldValueRetriever.cp294(),
      fieldValueRetriever.chooseCp997(),
      fieldValueRetriever.cp999())
  }

}
