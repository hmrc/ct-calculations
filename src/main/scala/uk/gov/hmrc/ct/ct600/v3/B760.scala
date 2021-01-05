/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP251

case class B760(value: Int) extends CtBoxIdentifier("FYA Claimed on Machinery and Plant") with CtInteger

object B760 extends Linked[CP251, B760] {

  override def apply(source: CP251): B760 = B760(source.value)
}
