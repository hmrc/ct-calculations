/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP668

case class B105(value: Option[Int]) extends CtBoxIdentifier("Capital Allowances(Machinery & Plant - special rate pool)") with CtOptionalInteger

object B105 extends Linked[CP668, B105] {

  override def apply(source: CP668): B105 = B105(source.value)
}
