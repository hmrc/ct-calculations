

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP247

case class B710(value: Option[Int]) extends CtBoxIdentifier("Balancing charges in main pool") with CtOptionalInteger

object B710 extends Linked[CP247, B710] {

  override def apply(source: CP247): B710 = B710(source.value)
}
