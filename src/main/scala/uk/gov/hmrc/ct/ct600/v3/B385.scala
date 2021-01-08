/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.calculations.{CorporationTaxCalculatorParameters, NINonTradingProfitCalculationParameters}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.computations.losses._

//was B54
case class B385(value: Int) extends CtBoxIdentifier("Amount of Profit FY2") with CtInteger

object B385 extends CorporationTaxCalculator with Calculated[B385, CT600BoxRetriever] {

  /*
  Same calculation as B54 Amount of Profit but in this case I2 Total of basic profit pro-rata for FY2
  must be calculated using B315v3 as an input to the MRR calculator, instead of B37 Profits chargeable to corporation tax
   */
  override def calculate(fieldValueRetriever: CT600BoxRetriever): B385 = {

    if(northernIrelandJourneyActive(fieldValueRetriever)){

      calculateNIApportionedNonTradingProfitsChargeableFy2(
        NINonTradingProfitCalculationParameters(
          fieldValueRetriever.cato23(),
          HmrcAccountingPeriod(fieldValueRetriever.cp1(),fieldValueRetriever.cp2())
        )
      )

    }else{

      calculateApportionedProfitsChargeableFy2(
        CorporationTaxCalculatorParameters(
          fieldValueRetriever.cp295(),
          HmrcAccountingPeriod(fieldValueRetriever.cp1(),fieldValueRetriever.cp2())
        ))

    }
  }
}
