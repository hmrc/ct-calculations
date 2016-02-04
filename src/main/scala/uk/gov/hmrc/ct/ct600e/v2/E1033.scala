/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.{CP1, CP2, HmrcAccountingPeriod}
import uk.gov.hmrc.ct.ct600.v2.B43
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E1033(value: Int) extends CtBoxIdentifier("First Financial Year") with CtInteger

object E1033 extends CorporationTaxCalculator with Calculated[E1033, CT600EBoxRetriever] {

  override def calculate(fieldValueRetriever: CT600EBoxRetriever): E1033 = {
    val cp43: B43 = financialYear1(
      HmrcAccountingPeriod(CP1(fieldValueRetriever.retrieveE1021().value), CP2(fieldValueRetriever.retrieveE1022().value))
    )
    E1033(cp43.value)
  }
}