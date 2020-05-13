

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.ExpensesCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP38(value: Int) extends CtBoxIdentifier(name = "Total Expenses") with CtInteger

object CP38 extends Calculated[CP38, ComputationsBoxRetriever] with ExpensesCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP38 =
    calculateTotalExpenses(cato14 = fieldValueRetriever.cato14(),
                           cato15 = fieldValueRetriever.cato15(),
                           cato16 = fieldValueRetriever.cato16())
}
