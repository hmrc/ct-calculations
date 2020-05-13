

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.{AnnualInvestmentAllowanceCalculator, AnnualInvestmentAllowancePeriods}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


case class CATO02(value: Int) extends CtBoxIdentifier(name = "Maximum Annual Investment Allowance") with CtInteger

object CATO02 extends Calculated[CATO02, ComputationsBoxRetriever] with AnnualInvestmentAllowanceCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO02 = {
    maximum(cp1 = fieldValueRetriever.cp1(),
            cp2 = fieldValueRetriever.cp2(),
            allowableAmounts = AnnualInvestmentAllowancePeriods())
  }
}
