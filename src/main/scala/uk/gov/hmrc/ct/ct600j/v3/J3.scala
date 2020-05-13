

package uk.gov.hmrc.ct.ct600j.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtDate, Linked}
import uk.gov.hmrc.ct.ct600.v3.B30


case class J3(value: LocalDate) extends CtBoxIdentifier(name = "AP Start date") with CtDate

object J3 extends Linked[B30, J3] {

  override def apply(source: B30): J3 = J3(source.value)
}
