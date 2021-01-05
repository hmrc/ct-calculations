/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.{CP297, CP298}

case class B711(value: Option[Int]) extends CtBoxIdentifier with CtOptionalInteger


object B711 extends Linked[CP297, B711] {

  override def apply(source: CP297): B711 = B711(source.value)
}
