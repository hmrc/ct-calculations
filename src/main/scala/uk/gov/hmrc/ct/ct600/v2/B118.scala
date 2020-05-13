

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP251


case class B118(value: Int) extends CtBoxIdentifier("Expenditure on Machinery and Plant") with CtInteger

object B118 extends Linked[CP251, B118] {

  override def apply(source: CP251): B118 = B118(source.value)
}
