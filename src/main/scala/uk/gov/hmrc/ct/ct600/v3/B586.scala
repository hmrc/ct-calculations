

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.calculations.B586Calculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B586(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "NI Corporation Tax included") with CtOptionalBigDecimal

object B586 extends Calculated[B586,CT600BoxRetriever] with B586Calculator {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B586 = {

    calculateB586(fieldValueRetriever.b360(), fieldValueRetriever.b410(), fieldValueRetriever.b330(), fieldValueRetriever.b380())

  }

}
