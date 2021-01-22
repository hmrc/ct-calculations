/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP122

case class B471(value: Option[Int]) extends CtBoxIdentifier("CJRS and JSS received") with CtOptionalInteger

object B471 extends Linked[CP122, B471] {

  override def apply(source: CP122): B471 = B471(source.value)
}