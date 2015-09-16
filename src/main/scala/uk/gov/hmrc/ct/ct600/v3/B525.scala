package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

// was B86
case class B525(value: BigDecimal) extends CtBoxIdentifier(name = "Self-assessment of tax payable") with CtBigDecimal

object B525 extends CorporationTaxCalculator with Calculated[B525, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B525 =
      calculateSATaxPayable(
        fieldValueRetriever.retrieveB510(),
        fieldValueRetriever.retrieveB515()
      )
}