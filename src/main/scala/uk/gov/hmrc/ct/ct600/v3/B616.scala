/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Linked}
import uk.gov.hmrc.ct.ct600ei.v3.DIT002

case class B616(value: Option[Boolean]) extends CtBoxIdentifier("Yes - goods") with CtOptionalBoolean

object B616 extends Linked[DIT002, B616] {
  override def apply(source: DIT002): B616 = B616(source.value)
}
