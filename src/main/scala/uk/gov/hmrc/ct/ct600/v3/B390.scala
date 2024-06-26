/*
 * Copyright 2024 HM Revenue & Customs
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

import uk.gov.hmrc.ct.box.{AnnualConstant, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

// was B55
case class B390(value: BigDecimal) extends CtBoxIdentifier("Rate Of Tax FY2") with CtBigDecimal with AnnualConstant

object B390 extends CorporationTaxCalculator with Calculated[B390, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B390 = {
    B390(rateOfTaxFy2(HmrcAccountingPeriod(fieldValueRetriever.cp1(),fieldValueRetriever.cp2()),
      fieldValueRetriever.b385(),fieldValueRetriever.b620(),
      fieldValueRetriever.b328()))
  }
}
