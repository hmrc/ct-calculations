/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.A80


case class B480(value: Option[BigDecimal]) extends CtBoxIdentifier(name = "Tax payable under S419 ICTA 1988") with AnnualConstant with CtOptionalBigDecimal

object B480 extends Linked[A80, B480] {

  override def apply(source: A80): B480 = B480(source.value)
}
