

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B65(value: BigDecimal) extends CtBoxIdentifier("Corporation Tax net of MRR") with CtBigDecimal

object B65 extends CorporationTaxCalculator with Calculated[B65, CT600BoxRetriever]{

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B65 =
    corporationTaxNetOfMrr(fieldValueRetriever.b46(),
                           fieldValueRetriever.b56(),
                           fieldValueRetriever.b64()
    )
}
