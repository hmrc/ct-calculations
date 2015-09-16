package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}


case class CP265(value: Int) extends CtBoxIdentifier("Profits before other deductions and reliefs (box 21)") with CtInteger

object CP265 extends Linked[CP293, CP265]{

  override def apply(source: CP293): CP265 = CP265(source.value)
}
