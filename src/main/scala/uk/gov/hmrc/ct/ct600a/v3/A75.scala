

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A75(value: Option[Int]) extends CtBoxIdentifier(name = "A75 - Total of all loans outstanding at end of return period - including all loans outstanding at the end of the return period, whether they were made in this period or an earlier one")
with CtOptionalInteger

object A75 extends Calculated[A75, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

 override def calculate(fieldValueRetriever: CT600ABoxRetriever): A75 = {
  calculateA75(fieldValueRetriever.a15(), fieldValueRetriever.lp04())
 }
}
