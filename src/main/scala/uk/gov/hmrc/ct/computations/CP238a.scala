/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP238a(value: Option[Int]) extends CtBoxIdentifier(name = "Losses used against non trading profits") with CtOptionalInteger

object CP238a extends Linked[CP997Abstract, CP238a] {

  override def apply(source: CP997Abstract): CP238a = CP238a(source.value)
}
