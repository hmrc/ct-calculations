/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Linked}
import uk.gov.hmrc.ct.ct600ei.v3.DIT001

case class B618(value: Option[Boolean]) extends CtBoxIdentifier("Yes - goods") with CtOptionalBoolean

object B618 extends Linked[DIT001, B618] {
  override def apply(source: DIT001): B618 = B618(source.value)
}
