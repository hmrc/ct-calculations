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

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

// B85
case class B520(value: Option[BigDecimal]) extends CtBoxIdentifier("Income Tax Repayable to the company") with CtOptionalBigDecimal

object B520 extends CorporationTaxCalculator with Calculated[B520, CT600BoxRetriever]{

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B520 = {
    calculateIncomeTaxRepayable(fieldValueRetriever.b515(), fieldValueRetriever.b510())
  }
}
