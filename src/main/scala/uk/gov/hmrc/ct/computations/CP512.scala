package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}


case class CP512(value: Int) extends CtBoxIdentifier with CtInteger

object CP512 extends Linked[CP511, CP512]{

  override def apply(source: CP511): CP512 = CP512(source.value)
}
