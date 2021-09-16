/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Linked}
import uk.gov.hmrc.ct.ct600ei.v3.DIT003

case class B617(value: Option[Boolean]) extends CtBoxIdentifier("Yes - services") with CtOptionalBoolean

object B617 extends Linked[DIT003, B617] {
  override def apply(source: DIT003): B617 = B617(source.value)
}
