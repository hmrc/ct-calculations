

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A45(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "A45 Relief due for loans repaid, released or written off (after the end of the period but earlier than nine months and one day after the end of the period)")
with CtOptionalBigDecimal

object A45 extends Calculated[A45, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

 override def calculate(fieldValueRetriever: CT600ABoxRetriever): A45 = {
  calculateA45(fieldValueRetriever.a40(), fieldValueRetriever.loansToParticipators(), fieldValueRetriever.cp2())
 }
}
