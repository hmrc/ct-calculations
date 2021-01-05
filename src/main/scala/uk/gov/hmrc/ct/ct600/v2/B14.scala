/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP515


case class B14(value: Option[Int]) extends CtBoxIdentifier("Annual profits and gains not falling under and other heading") with CtOptionalInteger

object B14 extends Linked[CP515, B14] {

  override def apply(source: CP515): B14 = B14(source.value)
}
