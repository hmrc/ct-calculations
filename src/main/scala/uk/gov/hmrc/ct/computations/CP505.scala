/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP505(value: Option[Int]) extends CtBoxIdentifier(name = "Ancillary Income") with CtOptionalInteger

object CP505 extends Linked[CP502, CP505] {

  override def apply(source: CP502): CP505 = CP505(source.value)
}
