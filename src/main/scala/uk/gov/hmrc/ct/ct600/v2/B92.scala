

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxAlreadyPaidCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B92(value: BigDecimal) extends CtBoxIdentifier("Corporation Tax outstanding") with CtBigDecimal

object B92 extends CorporationTaxAlreadyPaidCalculator with Calculated[B92, CT600BoxRetriever]{

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B92 =
    corporationTaxOutstanding(fieldValueRetriever.b86(),
                              fieldValueRetriever.b91())
}
