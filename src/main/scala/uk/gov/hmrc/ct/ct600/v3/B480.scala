package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever


case class B480(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "Tax payable under S419 ICTA 1988") with AnnualConstant with CtOptionalBigDecimal

object B480 extends CorporationTaxCalculator with Calculated[B480, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B480 = {
    // TODO: replace with real calculation once loans to participators stuff is implemented
    B480(None)
  }
}