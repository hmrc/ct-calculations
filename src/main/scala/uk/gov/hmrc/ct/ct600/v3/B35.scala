package uk.gov.hmrc.ct.ct600.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtDate, CtBoxIdentifier, Linked}
import uk.gov.hmrc.ct.computations.CP2

case class B35(value: LocalDate) extends CtBoxIdentifier(name = "AP end date") with CtDate

object B35 extends Linked[CP2, B35] {

  override def apply(source: CP2): B35 = B35(source.value)
}