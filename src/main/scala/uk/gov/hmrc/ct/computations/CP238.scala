/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP238(value: Option[Int]) extends CtBoxIdentifier(name = "Losses used against trading profits") with CtOptionalInteger

object CP238 extends Linked[CP290, CP238] {

  override def apply(source: CP290): CP238 = CP238(source.value)
}
