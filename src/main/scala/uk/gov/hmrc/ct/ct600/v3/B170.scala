/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP259

case class B170(value: Int) extends CtBoxIdentifier(name = "Bank, building society or other interest, and profits from non-trading loan relationships") with CtInteger

object B170 extends Linked[CP259, B170] {

  override def apply(source: CP259): B170 = B170(source.value)
}
