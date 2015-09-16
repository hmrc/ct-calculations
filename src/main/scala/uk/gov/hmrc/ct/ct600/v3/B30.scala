package uk.gov.hmrc.ct.ct600.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtDate, CtBoxIdentifier, Linked}
import uk.gov.hmrc.ct.computations.CP1

case class B30(value: LocalDate) extends CtBoxIdentifier(name = "AP Start date") with CtDate

object B30 extends Linked[CP1, B30] {

  override def apply(source: CP1): B30 = B30(source.value)
}