/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP265

case class B21(value: Int) extends CtBoxIdentifier("Profits before other deductions and reliefs") with CtInteger

object B21 extends Linked[CP265, B21] {

  override def apply(source: CP265): B21 = B21(source.value)
}
