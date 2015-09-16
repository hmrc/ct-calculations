package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B15(value: Int) extends CtBoxIdentifier(name = "Income within Sch D Case VI") with CtInteger

//TODO
object B15 extends Calculated[B15, CT600BoxRetriever] {
  override def calculate(fieldValueRetriever: CT600BoxRetriever): B15 = {
//    B15(fieldValueRetriever.retrieveB12() + fieldValueRetriever.retrieveB13() + fieldValueRetriever.retrieveB14())
    ???
  }

}
