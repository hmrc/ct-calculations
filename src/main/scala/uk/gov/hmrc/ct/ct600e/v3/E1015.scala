

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{AnnualConstant, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E1015(value: BigDecimal) extends CtBoxIdentifier("Second Financial Year Rate Of Tax") with CtBigDecimal with AnnualConstant

object E1015 extends CorporationTaxCalculator with Calculated[E1015, CT600EBoxRetriever] {

  override def calculate(fieldValueRetriever: CT600EBoxRetriever): E1015 = E1015(rateOfTaxFy2(fieldValueRetriever.e4))
}
