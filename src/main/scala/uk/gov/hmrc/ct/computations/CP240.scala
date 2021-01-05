/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP240(value: Option[Int]) extends CtBoxIdentifier(name = "Losses carried forward") with CtOptionalInteger

object CP240 extends Linked[CP288, CP240] {

  override def apply(source: CP288): CP240 = CP240(source.value)
}
