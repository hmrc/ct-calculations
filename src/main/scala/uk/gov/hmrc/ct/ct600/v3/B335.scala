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

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.losses._
import uk.gov.hmrc.ct.ct600.calculations.{CorporationTaxCalculatorParameters, NINonTradingProfitCalculationParameters}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
// was B44
case class B335(value: Int) extends CtBoxIdentifier("Amount of profit with tax rate: B340 FY1") with CtInteger

object B335 extends CorporationTaxCalculator with Calculated[B335, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B335 = {

    if(northernIrelandJourneyActive(fieldValueRetriever)){

      calculateNIApportionedNonTradingProfitsChargeableFy1(
        NINonTradingProfitCalculationParameters(
          fieldValueRetriever.cato23(),
          HmrcAccountingPeriod(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())
        )
      )

    }else{

      calculateApportionedProfitsChargeableFy1(
        CorporationTaxCalculatorParameters(
          fieldValueRetriever.cp295(),
          HmrcAccountingPeriod(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())
        )
      )
    }
  }
}
