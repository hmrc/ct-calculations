/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP7

case class B145(value: Option[Int]) extends CtBoxIdentifier(name = "Total turnover from trade") with CtOptionalInteger

object B145 extends Linked[CP7, B145] {

  override def apply(source: CP7): B145 = B145(source.value)
}
