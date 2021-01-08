/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandCalculations
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B360(value: Option[BigDecimal]) extends CtBoxIdentifier("Tax FY1") with CtOptionalBigDecimal

object B360 extends NorthernIrelandCalculations with Calculated[B360, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B360 = {
    calculateTaxForTradingProfitForFirstFinancialYear(fieldValueRetriever.b350(), fieldValueRetriever.b355())
  }

}
