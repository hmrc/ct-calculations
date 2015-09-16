package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtOptionalBigDecimal, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator

// B85
case class B520(value: Option[BigDecimal]) extends CtBoxIdentifier("Income Tax Repayable to the company") with CtOptionalBigDecimal

object B520 extends CorporationTaxCalculator with Calculated[B520, CT600BoxRetriever]{

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B520 = {
    calculateIncomeTaxRepayable(fieldValueRetriever.retrieveB515(), fieldValueRetriever.retrieveB510())
  }
}
