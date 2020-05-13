

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A70(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "A70 - Relief due now for loans repaid, released or written off more than nine months after the end of the period")
with CtOptionalBigDecimal

object A70 extends Calculated[A70, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

 override def calculate(fieldValueRetriever: CT600ABoxRetriever): A70 = {
  calculateA70(fieldValueRetriever.a65(), fieldValueRetriever.loansToParticipators(), fieldValueRetriever.cp2(), fieldValueRetriever.lpq07())
 }
}
