package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{AnnualConstant, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

// was B55
case class B390(value: BigDecimal) extends CtBoxIdentifier("Rate Of Tax FY2") with CtBigDecimal with AnnualConstant

object B390 extends CorporationTaxCalculator with Calculated[B390, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B390 = {
    rateOfTaxFy2(fieldValueRetriever.retrieveCP2())
  }
}
