/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP248

case class B705(value: Option[Int]) extends CtBoxIdentifier("Total capital allowances claimed in main pool") with CtOptionalInteger

object B705 extends Linked[CP248, B705] {

  override def apply(source: CP248): B705 = B705(source.value)
}
