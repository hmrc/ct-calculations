/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}


case class CP292(value: Int) extends CtBoxIdentifier("Interest received") with CtInteger

object CP292 extends Linked[CP58, CP292]{

  override def apply(source: CP58): CP292 = CP292(source.value)
}
