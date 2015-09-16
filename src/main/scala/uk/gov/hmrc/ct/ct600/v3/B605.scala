package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtOptionalBigDecimal, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B605(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "Tax overpaid including surplus or payable credits") with CtOptionalBigDecimal

object B605 extends CorporationTaxCalculator with Calculated[B605, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B605 = {
    calculateTaxOverpaid(fieldValueRetriever.retrieveB595(), fieldValueRetriever.retrieveB525())
  }
}
