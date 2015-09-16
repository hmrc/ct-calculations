package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, AnnualConstant, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator

// was B45
case class B340(value: BigDecimal) extends CtBoxIdentifier(name = "Rate Of Tax FY1") with AnnualConstant with CtBigDecimal

object B340 extends CorporationTaxCalculator with Calculated[B340, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B340 = {
    rateOfTaxFy1(fieldValueRetriever.retrieveCP1())
  }
}