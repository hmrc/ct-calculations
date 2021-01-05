/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP256

case class B3(value: Int) extends CtBoxIdentifier("Trading and professional profits") with CtInteger

object B3 extends Linked[CP256, B3] {

  override def apply(source: CP256): B3 = B3(source.value)
}
