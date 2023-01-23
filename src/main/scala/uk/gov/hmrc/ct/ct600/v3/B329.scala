package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

case class B329(value: Boolean) extends CtBoxIdentifier(name = "claiming SPR or MRR")  with CtBoolean

object B329 extends Calculated[B329,CT600BoxRetriever] {

  override def calculate(boxRetriever: CT600BoxRetriever): B329 = {
    val placeholder = true
    if (placeholder) {
      B329(true)
    } else {
      B329(false)
    }
  }
}
