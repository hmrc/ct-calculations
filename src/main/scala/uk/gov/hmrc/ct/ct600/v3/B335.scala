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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.calculations.CorporationTaxCalculatorParameters
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator

// was B44
case class B335(value: Int) extends CtBoxIdentifier("Amount of profit FY1") with CtInteger

object B335 extends CorporationTaxCalculator with Calculated[B335, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B335 = {
      calculateApportionedProfitsChargeableFy1(
        CorporationTaxCalculatorParameters(
          fieldValueRetriever.cp295(),
          HmrcAccountingPeriod(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())
        )
      )
  }
}
