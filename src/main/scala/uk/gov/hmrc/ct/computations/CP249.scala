/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP249(value: Option[Int]) extends CtBoxIdentifier(name = "Trade Annual Investment Allowance") with CtOptionalInteger

object CP249 extends Linked[CP88, CP249] {

  override def apply(source: CP88): CP249 = CP249(source.value)
}
