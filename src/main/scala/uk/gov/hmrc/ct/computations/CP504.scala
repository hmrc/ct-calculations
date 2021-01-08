/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP504(value: Int) extends CtBoxIdentifier(name = "Income from property") with CtInteger

object CP504 extends Linked[CP501, CP504] {

  override def apply(source: CP501): CP504 = CP504(source.orZero)
}
