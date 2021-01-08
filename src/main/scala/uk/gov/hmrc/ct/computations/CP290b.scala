/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP290b(value: Option[Int]) extends CtBoxIdentifier(name = "Post 1/4/17 losses brought forward against TP") with CtOptionalInteger

object CP290b extends Linked[CP283b, CP290b] {
  def apply(source: CP283b): CP290b = CP290b(source.value)
}
