

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP265

case class B235(value: Int) extends CtBoxIdentifier(name = "Profits before other deductions and reliefs") with CtInteger

object B235 extends Linked[CP265, B235] {

  override def apply(source: CP265): B235 = B235(source.value)
}
