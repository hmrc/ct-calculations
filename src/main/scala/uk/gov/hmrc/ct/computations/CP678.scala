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
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

import scala.math.BigDecimal.RoundingMode

case class CP678(value: Option[Int]) extends CtBoxIdentifier(name = "super deductions balancing charge")  with CtOptionalInteger


object CP678 extends Calculated[CP678, ComputationsBoxRetriever] with CtTypeConverters{
  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP678 = {
    if(superdeductions.isThereSuperDeductionOverLap(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())) {
      CP678(fieldValueRetriever.cp676().value.map { x =>
        (x * superdeductions.superDeductionsPercentage(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())).setScale(0, RoundingMode.DOWN).toInt
      })
    } else CP678(None)
  }
}