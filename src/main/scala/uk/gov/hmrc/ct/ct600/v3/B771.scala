/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP296

case class B771(value: Option[Int])extends CtBoxIdentifier with CtOptionalInteger

object B771 extends Linked[CP296, B771] {

  override def apply(source: CP296): B771 = B771(source.value)
}
