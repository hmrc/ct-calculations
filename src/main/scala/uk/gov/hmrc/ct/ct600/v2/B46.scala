

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B46(value: BigDecimal) extends CtBoxIdentifier("Tax") with CtBigDecimal

object B46 extends CorporationTaxCalculator with Calculated[B46, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B46 =
    corporationTaxFy1(fieldValueRetriever.b44(),
                      fieldValueRetriever.b45())
}
