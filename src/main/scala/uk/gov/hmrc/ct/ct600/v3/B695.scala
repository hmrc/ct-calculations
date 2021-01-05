/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.CP668

// was B78
case class B695(value: Option[Int]) extends CtBoxIdentifier("Machinery and plant - special rate pool / Capital allowances") with CtOptionalInteger

object B695 extends Linked[CP668, B695] {
  override def apply(source: CP668): B695 = B695(source.value)
}
