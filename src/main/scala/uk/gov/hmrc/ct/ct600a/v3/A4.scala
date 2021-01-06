/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtDate, Linked}
import uk.gov.hmrc.ct.ct600.v3.B35


case class A4(value: LocalDate) extends CtBoxIdentifier(name = "AP End date") with CtDate

object A4 extends Linked[B35, A4] {

  override def apply(source: B35): A4 = A4(source.value)
}
