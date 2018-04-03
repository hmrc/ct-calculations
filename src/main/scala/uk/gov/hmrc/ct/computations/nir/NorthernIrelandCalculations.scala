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

package uk.gov.hmrc.ct.computations.nir

import uk.gov.hmrc.ct.box.StartDate
import uk.gov.hmrc.ct.computations.{CP997c, CP997e, CPQ19, HmrcAccountingPeriod}
import uk.gov.hmrc.ct.ct600.calculations.{Ct600AnnualConstants, HmrcValueApportioning, NorthernIrelandRate, TaxYear}

import scala.math.BigDecimal.RoundingMode

trait NorthernIrelandCalculations extends HmrcValueApportioning {

  def revaluedNirLossesAgainstNonTradingProfit(ct600AnnualConstants: Ct600AnnualConstants)
                                              (cp997c: CP997c,
                                               hmrcAccountingPeriod: HmrcAccountingPeriod,
                                               cpq19: CPQ19): CP997e = {

    CP997e(cp997c.value.map { nirLosses =>


      val niRatio = getNorthernIrelandTaxRevaluationRatio(hmrcAccountingPeriod.start,ct600AnnualConstants)

      val lossesAfterCPQ19 = if(cpq19.isTrue) nirLosses*niRatio.setScale(0, RoundingMode.DOWN).toInt else nirLosses

      val apportionedValues: Map[TaxYear, Int] = calculateApportionedValuesForAccountingPeriod(lossesAfterCPQ19, hmrcAccountingPeriod)
      apportionedValues.map {
        case (taxYear, apportionedLoss) =>
          val ctRates = ct600AnnualConstants.constantsForTaxYear(taxYear)
          ctRates match {
            case nir: NorthernIrelandRate => (BigDecimal.valueOf(apportionedLoss) * nir.revaluationRatio).setScale(0, RoundingMode.DOWN).toInt
            case _ => apportionedLoss
          }
      }.sum
    })
  }

  def getNorthernIrelandTaxRevaluationRatio(startDate: StartDate, annualConstants:Ct600AnnualConstants): BigDecimal =  annualConstants match {

    case nir:NorthernIrelandRate => nir.revaluationRatio
    case _ => annualConstants.constantsForTaxYear(TaxYear(startingFinancialYear(startDate))).rateOfTax
  }
}
