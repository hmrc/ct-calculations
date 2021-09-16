/*
 * Copyright 2021 HM Revenue & Customs
 *
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
