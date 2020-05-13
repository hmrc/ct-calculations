

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, NotInPdf}
import uk.gov.hmrc.ct.computations.calculations.ExpensesCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CATO15(value: Int) extends CtBoxIdentifier(name = "Property Expenses") with CtInteger with NotInPdf

object CATO15 extends Calculated[CATO15, ComputationsBoxRetriever] with ExpensesCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO15 = {
    calculatePropertyExpenses(cp22 = fieldValueRetriever.cp22(),
                              cp23 = fieldValueRetriever.cp23(),
                              cp24 = fieldValueRetriever.cp24())
  }
}
