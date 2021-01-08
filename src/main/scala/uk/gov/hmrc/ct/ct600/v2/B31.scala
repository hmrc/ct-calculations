/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Linked}
import uk.gov.hmrc.ct.computations.CPQ18

case class B31(value: Option[Boolean]) extends CtBoxIdentifier with CtOptionalBoolean

object B31 extends Linked[CPQ18, B31] {

  override def apply(source: CPQ18): B31 = B31(source.value)
}
