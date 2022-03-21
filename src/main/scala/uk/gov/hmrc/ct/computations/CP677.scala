/*
 * Copyright 2022 HM Revenue & Customs
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

case class CP677(value: Option[Int]) extends CtBoxIdentifier(name = "super deduction claim amount")  with CtOptionalInteger

object CP677 extends Calculated[CP677, ComputationsBoxRetriever] with CtTypeConverters{
  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP677 = {
    if(superdeductions.isThereSuperDeductionOverLap(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())) {
      CP677(fieldValueRetriever.cp675().value.map { x =>
        (x * superdeductions.superDeductionsPercentage(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())).setScale(0, RoundingMode.UP).toInt
      })
    } else CP677(None)
  }
}
