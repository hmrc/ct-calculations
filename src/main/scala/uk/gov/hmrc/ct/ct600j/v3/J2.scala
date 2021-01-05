/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Linked}
import uk.gov.hmrc.ct.ct600.v3.B3


case class J2(value: String) extends CtBoxIdentifier(name = "Tax reference") with CtString

object J2 extends Linked[B3, J2] {

  override def apply(source: B3): J2 = J2(source.value)
}
