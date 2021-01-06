/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Linked}
import uk.gov.hmrc.ct.ct600.v3.B1


case class A1(value: String) extends CtBoxIdentifier(name = "Company name") with CtString

object A1 extends Linked[B1, A1] {

  override def apply(source: B1): A1 = A1(source.value)
}
