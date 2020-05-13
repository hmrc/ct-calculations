

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

// B85
case class B520(value: Option[BigDecimal]) extends CtBoxIdentifier("Income Tax Repayable to the company") with CtOptionalBigDecimal

object B520 extends CorporationTaxCalculator with Calculated[B520, CT600BoxRetriever]{

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B520 = {
    calculateIncomeTaxRepayable(fieldValueRetriever.b515(), fieldValueRetriever.b510())
  }
}
