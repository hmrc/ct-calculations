/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.calculations.B586Calculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B586(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "NI Corporation Tax included") with CtOptionalBigDecimal

object B586 extends Calculated[B586,CT600BoxRetriever] with B586Calculator {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B586 = {

    calculateB586(fieldValueRetriever.b345(), fieldValueRetriever.b395(), fieldValueRetriever.b330(), fieldValueRetriever.b380())

  }

}
