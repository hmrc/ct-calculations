package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}


case class CP251(value: Int) extends CtBoxIdentifier("Expenditure on machinery and plant on which first year allowance is claimed") with CtInteger

object CP251 extends Linked[CP87, CP251]{

  override def apply(source: CP87): CP251 = CP251(source.value)
}
