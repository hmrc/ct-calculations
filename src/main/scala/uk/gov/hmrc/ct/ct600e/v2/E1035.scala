

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{AnnualConstant, Calculated, CtBigDecimal, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator
import uk.gov.hmrc.ct.ct600.v2.{B37, B38, B39, B42}
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever


case class E1035(value: BigDecimal) extends CtBoxIdentifier(name = "First Financial Year Rate Of Tax") with AnnualConstant with CtBigDecimal

object E1035 extends CorporationTaxCalculator with Calculated[E1035, CT600EBoxRetriever] {

  override def calculate(fieldValueRetriever: CT600EBoxRetriever): E1035 =
    E1035(rateOfTaxFy1(HmrcAccountingPeriod(fieldValueRetriever.e1021(), fieldValueRetriever.e1022()),
                       B37(0),
                       B42(false),
                       B39(None),
                       B38(None)))
}
