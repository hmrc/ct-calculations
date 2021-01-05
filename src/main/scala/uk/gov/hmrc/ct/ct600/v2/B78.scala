/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, Linked}

case class B78(value: BigDecimal) extends CtBoxIdentifier("Net Corporation Tax liability") with CtBigDecimal

object B78 extends Linked[B65, B78] {

  override def apply(source: B65): B78 = B78(source.value)
}
