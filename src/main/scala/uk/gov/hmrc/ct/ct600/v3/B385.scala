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

//was B54
case class B385(value: Int) extends CtBoxIdentifier("Amount of Profit FY2") with CtInteger

object B385 extends CorporationTaxCalculator with Calculated[B385, ComputationsBoxRetriever] {

  /*
  Same calculation as B54 Amount of Profit but in this case I2 Total of basic profit pro-rata for FY2
  must be calculated using B315v3 as an input to the MRR calculator, instead of B37 Profits chargeable to corporation tax
   */
  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B385 = {
    calculateApportionedProfitsChargeableFy2(
      CorporationTaxCalculatorParameters(
        fieldValueRetriever.cp295(),
        HmrcAccountingPeriod(fieldValueRetriever.cp1(),fieldValueRetriever.cp2())
      ))
  }
}
