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

case class B400(value: Option[Int]) extends CtBoxIdentifier(name = "Net trading profits") with CtOptionalInteger

object B400 extends NorthernIrelandCalculations with Calculated[B400, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B400 = {
    B400(nirOpt(fieldValueRetriever)(
      calculateNIApportionedTradingProfitsChargeableFy2(
        NITradingProfitCalculationParameters(
          fieldValueRetriever.cp291(),
          HmrcAccountingPeriod(fieldValueRetriever.cp1(), fieldValueRetriever.cp2())
        )
      )
    ))
  }
}
