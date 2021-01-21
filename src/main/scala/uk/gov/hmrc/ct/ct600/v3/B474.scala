/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3


import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, CtInteger, CtOptionalBigDecimal, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP125

case class B474(value: Option[Int]) extends CtBoxIdentifier("JRB and EOTHO overpayments") with CtOptionalInteger

object B474 extends Linked[CP125, B474] {

  override def apply(source: CP125): B474 = B474(source.value)
}