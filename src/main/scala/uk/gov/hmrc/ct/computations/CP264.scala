/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP264(value: Int) extends CtBoxIdentifier("Trading losses of this or a later AP (box 30)") with CtInteger

object CP264 extends Linked[CP239, CP264]{

  override def apply(source: CP239): CP264 = CP264(source.value)
}
