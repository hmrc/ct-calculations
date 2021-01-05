/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, Linked, StartDate}

case class B30(value: LocalDate) extends CtBoxIdentifier(name = "AP Start date") with StartDate

object B30 extends Linked[StartDate, B30] {

  override def apply(source: StartDate): B30 = B30(source.value)
}
