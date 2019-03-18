/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A3(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "A3 - Tax chargeable on loans - (Tax due before any relief for loans repaid, released, or written off after the end of the period)")
 with CtOptionalBigDecimal

object A3 extends Calculated[A3, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A3 = {
    calculateA3(fieldValueRetriever.a2())
  }
}
