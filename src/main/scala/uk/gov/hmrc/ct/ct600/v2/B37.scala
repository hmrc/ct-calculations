/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP295


case class B37(value: Int) extends CtBoxIdentifier(name = "Profits chargeable to corporation tax") with CtInteger

object B37 extends Linked[CP295, B37] {

  override def apply(source: CP295): B37 = B37(source.value)
}
