package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box._

case class B140(value: Option[Boolean]) extends CtBoxIdentifier("Disclosure of Tax Avoidance Schemes - form CT600J") with CtOptionalBoolean

object B140 extends Linked[B65, B140] {

  override def apply(source: B65): B140 = B140(source.value)

}
