/*
 * Copyright 2023 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger, CtTypeConverters}
import uk.gov.hmrc.ct.computations.calculations.NetSuperDeductionCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP680(value: Option[Int]) extends CtBoxIdentifier(name = "net super deduction balancing charge")  with CtOptionalInteger

object CP680 extends Calculated[CP680, ComputationsBoxRetriever] with NetSuperDeductionCalculator with CtTypeConverters {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP680 = {
    if(superdeductions.isThereSuperDeductionOverLap(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())) {
      netSuperDeductionBalancingCharge(fieldValueRetriever.cp677(), fieldValueRetriever.cp678)
    } else CP680(None)
  }
}
