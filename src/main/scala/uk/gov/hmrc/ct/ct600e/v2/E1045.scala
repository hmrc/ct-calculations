

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{AnnualConstant, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.{B37, B38, B39, B42}
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever


case class E1045(value: BigDecimal) extends CtBoxIdentifier("Second Financial Year Rate Of Tax") with CtBigDecimal with AnnualConstant

object E1045 extends CorporationTaxCalculator with Calculated[E1045, CT600EBoxRetriever] {

  override def calculate(fieldValueRetriever: CT600EBoxRetriever): E1045 =
    E1045(rateOfTaxFy2(HmrcAccountingPeriod(fieldValueRetriever.e1021, fieldValueRetriever.e1022),
                       B37(0),
                       B42(false),
                       B39(None),
                       B38(None)))
}
