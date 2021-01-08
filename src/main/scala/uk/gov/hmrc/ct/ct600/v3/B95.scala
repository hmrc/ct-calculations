/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600a.v3.LPQ01


case class B95(value: Boolean) extends CtBoxIdentifier("Loans and arrangements to participators by close companies") with CtBoolean

object B95 extends Linked[LPQ01, B95] {
  override def apply(source: LPQ01): B95 = B95(source.value)
}
