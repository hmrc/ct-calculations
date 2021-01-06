/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtDate, Linked}
import uk.gov.hmrc.ct.ct600.v3.B30


case class A3(value: LocalDate) extends CtBoxIdentifier(name = "AP Start date") with CtDate

object A3 extends Linked[B30, A3] {

  override def apply(source: B30): A3 = A3(source.value)
}
