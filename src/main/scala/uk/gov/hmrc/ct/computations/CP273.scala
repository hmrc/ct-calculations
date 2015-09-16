package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}


case class CP273(value: Int) extends CtBoxIdentifier("Expenditure on machinery and plant") with CtInteger

object CP273 extends Linked[CP251, CP273]{

  override def apply(source: CP251): CP273 = CP273(source.value)
}
