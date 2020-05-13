

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600e.v2.calculations.LoansAndDebtorsCalculator
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E24e(value: Option[Int]) extends CtBoxIdentifier("Loans and non-trade debtors (Held at the end of the period)") with CtOptionalInteger

object E24e extends Calculated[E24e, CT600EBoxRetriever] with LoansAndDebtorsCalculator {
  override def calculate(boxRetriever: CT600EBoxRetriever): E24e =
    calculateFieldValue(
      boxRetriever.e24eA(),
      boxRetriever.e24eB()
    )
}
