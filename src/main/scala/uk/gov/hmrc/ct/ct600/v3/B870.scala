/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBigDecimal, Linked}

case class B870(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "Income Tax repayment owed") with CtOptionalBigDecimal

object B870 extends Linked[B520, B870] {
  override def apply(source: B520): B870 = B870(source.value)
}
