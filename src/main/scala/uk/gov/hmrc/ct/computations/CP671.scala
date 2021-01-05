/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP671(value: Option[Int]) extends CtBoxIdentifier with CtOptionalInteger

object CP671 extends Linked[CP91, CP671] {

  override def apply(source: CP91): CP671 = CP671(source.value)
}
