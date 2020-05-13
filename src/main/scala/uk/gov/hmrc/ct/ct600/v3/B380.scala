

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator

// was B53
case class B380(value: Option[Int]) extends CtBoxIdentifier("Financial Year FY2") with CtOptionalInteger

object B380 extends CorporationTaxCalculator with Calculated[B380, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B380 =
    B380(financialYear2(
      HmrcAccountingPeriod(fieldValueRetriever.cp1(),fieldValueRetriever.cp2())
    ))
}
