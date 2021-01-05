/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandCalculations
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B410(value: Option[BigDecimal]) extends CtBoxIdentifier("Tax FY2") with CtOptionalBigDecimal

object B410 extends NorthernIrelandCalculations with Calculated[B410, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B410 = {
    calculateTaxForTradingProfitForSecondFinancialYear(
      fieldValueRetriever.b400(), fieldValueRetriever.b405()
    )
  }

}
