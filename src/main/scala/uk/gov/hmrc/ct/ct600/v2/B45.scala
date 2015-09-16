package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{AnnualConstant, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever


case class B45(value: BigDecimal) extends CtBoxIdentifier(name = "Rate Of Tax") with AnnualConstant with CtBigDecimal

object B45 extends CorporationTaxCalculator with Calculated[B45, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B45 =
    rateOfTaxFy1(
      HmrcAccountingPeriod(fieldValueRetriever.retrieveCP1(),fieldValueRetriever.retrieveCP2()),
      fieldValueRetriever.retrieveB37(),
      fieldValueRetriever.retrieveB42(),
      fieldValueRetriever.retrieveB39(),
      fieldValueRetriever.retrieveB38()
    )
}
