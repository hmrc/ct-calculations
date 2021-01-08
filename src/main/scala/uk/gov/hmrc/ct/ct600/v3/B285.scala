/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.CP263

case class B285(value: Int) extends CtBoxIdentifier(name = "(Post reform) Trading losses carried forward and claimed against total profits") with CtInteger

object B285 extends Linked[CP263, B285] with CtTypeConverters {
  override def apply(source: CP263): B285 = B285(source.orZero)
}
