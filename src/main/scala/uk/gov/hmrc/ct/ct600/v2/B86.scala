

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.calculations.Ct600FinalisationCalculator
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B86(value: BigDecimal) extends CtBoxIdentifier("Tax Payable") with CtBigDecimal

object B86 extends Ct600FinalisationCalculator with Calculated[B86, CT600BoxRetriever]{

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B86 =
    computeTaxPayable(
        fieldValueRetriever.b70(),
        fieldValueRetriever.b79(),
        fieldValueRetriever.b84())
}
