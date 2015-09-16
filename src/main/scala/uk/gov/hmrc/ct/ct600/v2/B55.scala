package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{AnnualConstant, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B55(value: BigDecimal) extends CtBoxIdentifier("Rate Of Tax") with CtBigDecimal with AnnualConstant

object B55 extends CorporationTaxCalculator with Calculated[B55, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B55 =
    rateOfTaxFy2(HmrcAccountingPeriod(fieldValueRetriever.retrieveCP1(), fieldValueRetriever.retrieveCP2()),
                 fieldValueRetriever.retrieveB37(),
                 fieldValueRetriever.retrieveB42(),
                 fieldValueRetriever.retrieveB39(),
                 fieldValueRetriever.retrieveB38())
}
