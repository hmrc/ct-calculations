

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoolean, CtBoxIdentifier}
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B42(value: Boolean) extends CtBoxIdentifier("MRR or SCR Claimed") with CtBoolean

object B42 extends Calculated[B42, CT600BoxRetriever] {

  override def calculate(boxRetriever: CT600BoxRetriever): B42 = {
    B42(boxRetriever.b42a().orFalse || boxRetriever.b42b().orFalse)
  }

}
