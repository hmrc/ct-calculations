package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtInteger, CtBoxIdentifier, Linked}
import uk.gov.hmrc.ct.computations.CP264

case class B275(value: Int) extends CtBoxIdentifier(name = "Total trading losses of this or a later accounting period") with CtInteger

object B275 extends Linked[CP264, B275] {

  override def apply(source: CP264): B275 = B275(source.value)
}