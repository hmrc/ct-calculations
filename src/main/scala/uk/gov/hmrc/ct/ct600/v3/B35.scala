/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, EndDate, Linked}

case class B35(value: LocalDate) extends CtBoxIdentifier(name = "AP end date") with EndDate

object B35 extends Linked[EndDate, B35] {

  override def apply(source: EndDate): B35 = B35(source.value)
}
