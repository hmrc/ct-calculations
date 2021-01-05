/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.UTR
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Linked}

case class E2(value: String) extends CtBoxIdentifier("Tax Reference - UTR") with CtString

object E2 extends Linked[UTR, E2] {
  override def apply(source: UTR): E2 = E2(source.value)
}
