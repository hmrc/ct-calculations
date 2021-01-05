/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP253

case class B775(value: Int) extends CtBoxIdentifier("Other machinery and plant capital allowances") with CtInteger

object B775 extends Linked[CP253, B775] {

  override def apply(source: CP253): B775 = B775(source.value)
}
