/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP511

case class B190(value: Int) extends CtBoxIdentifier(name = "Income from a property business") with CtInteger

object B190 extends Linked[CP511, B190] {

  override def apply(source: CP511): B190 = B190(source.value)
}
