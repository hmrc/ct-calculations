/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP88

case class B172(value: Option[Int]) extends CtBoxIdentifier("Annual Investment Allowance") with CtOptionalInteger

object B172 extends Linked[CP88, B172] {

  override def apply(source: CP88): B172 = B172(source.value)
}
