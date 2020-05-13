

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B600(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "Total tax to pay") with CtOptionalBigDecimal

object B600 extends CorporationTaxCalculator with Calculated[B600, CT600BoxRetriever] {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B600 = {
    calculateTotalTaxToPay(fieldValueRetriever.b525(),fieldValueRetriever.b595())
  }
}
