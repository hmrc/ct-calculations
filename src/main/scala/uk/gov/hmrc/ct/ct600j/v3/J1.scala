/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Linked}
import uk.gov.hmrc.ct.ct600.v3.B1


case class J1(value: String) extends CtBoxIdentifier(name = "Company name") with CtString

object J1 extends Linked[B1, J1] {

  override def apply(source: B1): J1 = J1(source.value)
}
