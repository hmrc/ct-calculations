/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP511


case class B11(value: Int) extends CtBoxIdentifier("Income from UK land and buildings") with CtInteger

object B11 extends Linked[CP511, B11] {

  override def apply(source: CP511): B11 = B11(source.value)
}
