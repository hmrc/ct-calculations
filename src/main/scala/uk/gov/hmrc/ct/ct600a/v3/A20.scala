

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalBigDecimal}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class A20(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "A20 - Tax chargeable on loans - (Tax due before any relief for loans repaid, released, or written off after the end of the period)")
with CtOptionalBigDecimal

object A20 extends Calculated[A20, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

 override def calculate(fieldValueRetriever: CT600ABoxRetriever): A20 = {
  calculateA20(fieldValueRetriever.a15(), fieldValueRetriever.loansToParticipators(), fieldValueRetriever.cp2())
 }
}
