/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP248


case class B107(value: Option[Int]) extends CtBoxIdentifier("Capital Allowances (Machinery & Plant - main pool)") with CtOptionalInteger

object B107 extends Linked[CP248, B107] {

  override def apply(source: CP248): B107 = B107(source.value)
}
