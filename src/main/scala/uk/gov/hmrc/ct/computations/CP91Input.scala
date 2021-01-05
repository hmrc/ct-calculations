/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP91Input(value: Option[Int]) extends CtBoxIdentifier(name = "Balancing Charge Input") with CtOptionalInteger with Input

object CP91Input {

  def apply(int: Int): CP91Input = CP91Input(Some(int))

}
