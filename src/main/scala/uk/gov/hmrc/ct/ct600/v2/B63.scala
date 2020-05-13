

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B63(value: BigDecimal) extends CtBoxIdentifier("Corporation Tax") with CtBigDecimal

object B63 extends CorporationTaxCalculator with Calculated[B63, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B63 =
    totalCorporationTaxChargeable(fieldValueRetriever.b46(),
                                  fieldValueRetriever.b56())
}
