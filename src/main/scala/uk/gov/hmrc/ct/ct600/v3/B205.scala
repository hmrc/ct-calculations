/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP502

case class B205(value: Option[Int]) extends CtBoxIdentifier("Other income") with CtOptionalInteger

object B205 extends Linked[CP502, B205] {

  override def apply(source: CP502): B205 = B205(source.value)
}
