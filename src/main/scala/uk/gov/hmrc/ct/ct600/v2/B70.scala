

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B70(value: BigDecimal) extends CtBoxIdentifier("Corporation Tax Chargeable") with CtBigDecimal

object B70 extends CorporationTaxCalculator with Calculated[B70, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B70 =
    finalCorporationTaxChargeable(fieldValueRetriever.b42(),
                                  fieldValueRetriever.b46(),
                                  fieldValueRetriever.b56(),
                                  fieldValueRetriever.b64()
    )
}
