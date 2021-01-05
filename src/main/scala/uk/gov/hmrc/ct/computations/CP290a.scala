/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP290a(value: Option[Int]) extends CtBoxIdentifier(name = "Pre 1/4/17 losses brought forward against TP") with CtOptionalInteger

object CP290a extends Linked[CP283a, CP290a] {
  def apply(source: CP283a): CP290a = CP290a(source.value)
}
