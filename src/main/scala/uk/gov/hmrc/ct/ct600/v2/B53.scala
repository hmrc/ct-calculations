package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator


case class B53(value: Option[Int]) extends CtBoxIdentifier("Financial Year") with CtOptionalInteger

object B53 extends CorporationTaxCalculator with Calculated[B53, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B53 =
    financialYear2(
      HmrcAccountingPeriod(fieldValueRetriever.retrieveCP1(),fieldValueRetriever.retrieveCP2())
    )

}
