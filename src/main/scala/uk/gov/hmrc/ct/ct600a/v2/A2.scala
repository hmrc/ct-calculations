

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v2.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class A2(value: Option[Int]) extends CtBoxIdentifier(name = "A2 Total Loans made during the return period which have not been repaid, released or written off before the end of the period") with CtOptionalInteger

object A2 extends Calculated[A2, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): A2 = {
    calculateA2(fieldValueRetriever.lp02())
  }
}
