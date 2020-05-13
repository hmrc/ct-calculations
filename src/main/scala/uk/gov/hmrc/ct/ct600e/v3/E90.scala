

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600e.v3.calculations.IncomeCalculator
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

case class E90(value: Option[Int]) extends CtBoxIdentifier("Total of boxes E50 to E85") with CtOptionalInteger

object E90 extends Calculated[E90, CT600EBoxRetriever] with IncomeCalculator {
  override def calculate(boxRetriever: CT600EBoxRetriever): E90 = calculateTotalIncome(
    e50 = boxRetriever.e50(),
    e55 = boxRetriever.e55(),
    e60 = boxRetriever.e60(),
    e65 = boxRetriever.e65(),
    e70 = boxRetriever.e70(),
    e75 = boxRetriever.e75(),
    e80 = boxRetriever.e80(),
    e85 = boxRetriever.e85()
  )
}
