/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBigDecimal, Linked}

case class B865(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "Corporation Tax repayment owed") with CtOptionalBigDecimal

object B865 extends Linked[B605, B865] {
  override def apply(source: B605): B865 = B865(source.value)
}
