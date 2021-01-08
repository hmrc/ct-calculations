/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP58(value: Int) extends CtBoxIdentifier(name = "Non trade interest received") with CtInteger

object CP58 extends Linked[CP43, CP58] {

  override def apply(source: CP43): CP58 = CP58(source.orZero)
}
