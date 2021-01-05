/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP118

case class B780(value: Int) extends CtBoxIdentifier("Losses of trade carried out in the UK") with CtInteger

object B780 extends Linked[CP118, B780] {

  override def apply(source: CP118): B780 = B780(source.value)
}
