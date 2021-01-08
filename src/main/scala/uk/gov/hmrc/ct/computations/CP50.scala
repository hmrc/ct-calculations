/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP50(value: Option[Int]) extends CtBoxIdentifier(name = "Interest received") with CtOptionalInteger with Input

object CP50 {

  def apply(value: Int): CP50 = CP50(Some(value))
}
