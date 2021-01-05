/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP253

case class B121(value: Int) extends CtBoxIdentifier("Other Machinery and Plant") with CtInteger

object B121 extends Linked[CP253, B121] {

  override def apply(source: CP253): B121 = B121(source.value)
}
