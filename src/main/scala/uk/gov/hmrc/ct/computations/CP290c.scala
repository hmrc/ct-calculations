/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP290c(value: Option[Int]) extends CtBoxIdentifier(name = "Post 1/4/17 losses brought forward against Northern Ireland TP") with CtOptionalInteger

object CP290c extends Linked[CP283c, CP290c] {
  def apply(source: CP283c): CP290c = CP290c(source.value)
}
