/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtBigDecimal, Linked}
import uk.gov.hmrc.ct.computations.CP126



case class B526(value: BigDecimal) extends CtBoxIdentifier("Coronavirus support schemes overpayment now due") with CtBigDecimal

object B526 extends Linked[CP126, B526] {

  override def apply(source: CP126): B526 = B526(source.value)
}