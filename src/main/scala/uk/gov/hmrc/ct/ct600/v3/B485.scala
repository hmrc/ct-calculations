

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.calculations.LoansToParticipatorsCalculator
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever

case class B485(value: Boolean) extends CtBoxIdentifier("Put an 'X' in box 485 if you completed box A70 in the supplementary pages CT600A") with CtBoolean

object B485 extends Calculated[B485, CT600ABoxRetriever] with LoansToParticipatorsCalculator {

  override def calculate(fieldValueRetriever: CT600ABoxRetriever): B485 = {
    calculateB485(fieldValueRetriever.a70())
  }

}
