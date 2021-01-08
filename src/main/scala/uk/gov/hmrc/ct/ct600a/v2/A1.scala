/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Linked}

case class A1(value: Option[Boolean]) extends CtBoxIdentifier(name = "A1 Loans made during the period released or written off before the end of the period") with CtOptionalBoolean

object A1 extends Linked[LPQ09, A1] {

  override def apply(source: LPQ09): A1 = A1(source.value)
}
