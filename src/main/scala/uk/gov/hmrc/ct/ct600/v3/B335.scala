package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBigDecimal, Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.calculations.CorporationTaxCalculatorParameters
import uk.gov.hmrc.ct.ct600.v3.calculations.CorporationTaxCalculator

// was B44
case class B335(value: Int) extends CtBoxIdentifier("Amount of profit FY1") with CtInteger

object B335 extends CorporationTaxCalculator with Calculated[B335, ComputationsBoxRetriever] {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B335 = {
      calculateApportionedProfitsChargeableFy1(
        CorporationTaxCalculatorParameters(
          fieldValueRetriever.retrieveCP295(),
          HmrcAccountingPeriod(fieldValueRetriever.retrieveCP1(), fieldValueRetriever.retrieveCP2())
        )
      )
  }
}

