

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.ct600.v3.calculations.B325Calculator
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever


case class B325(value: Option[Int]) extends CtBoxIdentifier(name = "Northern Ireland profits included") with CtOptionalInteger

object B325 extends Calculated[B325,CT600BoxRetriever] with B325Calculator {

  override def calculate(fieldValueRetriever: CT600BoxRetriever): B325 = {

    calculateB325(fieldValueRetriever.b350(), fieldValueRetriever.b400(), fieldValueRetriever.b330(), fieldValueRetriever.b380(), fieldValueRetriever.b315())

  }
}