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

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{AnnualConstant, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E1005(value: BigDecimal) extends CtBoxIdentifier(name = "First Financial Year Rate Of Tax") with AnnualConstant with CtBigDecimal

object E1005 extends CorporationTaxCalculator with Calculated[E1005, CT600EBoxRetriever] {

  override def calculate(fieldValueRetriever: CT600EBoxRetriever): E1005 =
    E1005(rateOfTaxFy1(fieldValueRetriever.e3()))
}
