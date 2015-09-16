package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v2.calculations.CorporationTaxCalculator

case class B43(value: Int) extends CtBoxIdentifier("Financial Year") with CtInteger

object B43 extends CorporationTaxCalculator with Calculated[B43, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B43 =
    financialYear1(
      HmrcAccountingPeriod(fieldValueRetriever.retrieveCP1(), fieldValueRetriever.retrieveCP2())
    )
}

