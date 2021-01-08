/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandCalculations
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.calculations.NITradingProfitCalculationParameters

case class B350(value: Option[Int]) extends CtBoxIdentifier(name = "Amount of profit with tax rate: B355 FY1") with CtOptionalInteger

object B350 extends NorthernIrelandCalculations with Calculated[B350, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B350 = {
    B350(nirOpt(fieldValueRetriever)(
      calculateNIApportionedTradingProfitsChargeableFy1(
        NITradingProfitCalculationParameters(
          fieldValueRetriever.cp291(),
          HmrcAccountingPeriod(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())
        )
      )
    ))
  }
}
