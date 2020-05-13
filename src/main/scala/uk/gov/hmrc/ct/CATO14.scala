

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, NotInPdf}
import uk.gov.hmrc.ct.computations.calculations.ExpensesCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CATO14(value: Int) extends CtBoxIdentifier(name = "Directors Expenses") with CtInteger with NotInPdf

object CATO14 extends Calculated[CATO14, ComputationsBoxRetriever] with ExpensesCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO14 = {
    calculateDirectorsExpenses(cp15 = fieldValueRetriever.cp15(),
                               cp16 = fieldValueRetriever.cp16(),
                               cp17 = fieldValueRetriever.cp17(),
                               cp18 = fieldValueRetriever.cp18(),
                               cp19 = fieldValueRetriever.cp19(),
                               cp20 = fieldValueRetriever.cp20(),
                               cp21 = fieldValueRetriever.cp21())
  }
}
