/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP123

case class B472(value: Option[Int]) extends CtBoxIdentifier("CJRS and JSS entitlement") with CtOptionalInteger

object B472 extends Linked[CP123, B472] {

  override def apply(source: CP123): B472 = B472(source.value)
}