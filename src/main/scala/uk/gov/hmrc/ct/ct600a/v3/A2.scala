/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Linked}
import uk.gov.hmrc.ct.ct600.v3.B3


case class A2(value: String) extends CtBoxIdentifier(name = "Tax reference") with CtString

object A2 extends Linked[B3, A2] {

  override def apply(source: B3): A2 = A2(source.value)
}
