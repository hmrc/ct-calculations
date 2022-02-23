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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal, CtOptionalInteger, CtTypeConverters, SelfValidatableBox}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP677(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "super deduction claim amount")  with CtOptionalBigDecimal with SelfValidatableBox[ComputationsBoxRetriever, Option[BigDecimal]] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      validateZeroOrPositiveInteger()
    )
  }
}

object CP677 extends Calculated[CP677, ComputationsBoxRetriever] with CtTypeConverters {
  override def calculate(boxRetriever: ComputationsBoxRetriever): CP677 = {
    CP677(boxRetriever.cp675().value.map{ x =>
      x * superdeductions.superDeductionsPercentage(boxRetriever.cp1(), boxRetriever.cp2())
    })
  }
}
